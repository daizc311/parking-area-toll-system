package run.bequick.dreamccc.pats.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.text.StrFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.service.data.CarInfoDService;
import run.bequick.dreamccc.pats.service.data.CarParkingStatusDService;
import run.bequick.dreamccc.pats.service.data.ParkingCardDService;
import run.bequick.dreamccc.pats.service.data.ParkingSettingDService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class InOutStorageServiceImpl implements InOutStorageService {

    private final CarParkingStatusDService carParkingStatusDService;
    private final CarInfoDService carInfoDService;
    private final ParkingSettingDService parkingSettingDService;
    private final ParkingCardDService parkingCardDService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override

    @ServiceLog(value = "车辆入库 - {pos} - numberPlate:{}", paramEl = {"#root[0].carInfo.numberPlate"})
    public void inStorage(ApiInStorageParam param) {

        var simpleCarInfo = param.getCarInfo();
        var inStorageTime = param.getInStorageTime();
        var numberPlate = simpleCarInfo.getNumberPlate();

        CarInfo carInfo = carInfoDService.findByNumberPlate(numberPlate).orElseGet(() -> {

            var addCI = new CarInfo();
            addCI.setModelName(simpleCarInfo.getModelName());
            addCI.setNumberPlate(simpleCarInfo.getNumberPlate());
            addCI.setColor(simpleCarInfo.getColor());
            return carInfoDService.saveCarInfo(addCI);
        });

        carParkingStatusDService.addStorageStatus(carInfo, inStorageTime);
    }

    @Override

    public boolean feePayment(ApiFeePaymentParam param) {

        final var parkingCard = parkingCardDService.findById(param.getParkingCardId())
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到id为[{}]的停车卡", param.getParkingCardId())));
        final var carInfo = carInfoDService.findById(param.getCarInfoId())
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到id为[{}]的车辆信息", param.getCarInfoId())));
        final var carParkingStatus = carParkingStatusDService.findByCarInfo(carInfo)
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到车辆id为[{}]的在库状态", carInfo.getId())));

        return feePayment(carParkingStatus, param.getAmount(), parkingCard);
    }


    @Override
    public BigDecimal calcAmountToPaid(Long carId) {
        final var carInfo = carInfoDService.findById(carId)
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到id为[{}]的车辆信息", carId)));
        return calcAmountToPaid(carInfo);
    }

    public BigDecimal calcAmountToPaid(CarInfo carInfo) {
        final var carParkingStatus = carParkingStatusDService.findByCarInfo(carInfo)
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到车辆id为[{}]的在库状态", carInfo.getId())));
        return calcAmountToPaid(carParkingStatus);
    }

    public BigDecimal calcAmountToPaid(CarParkingStatus carParkingStatus) {
        final var setting = parkingSettingDService.getSetting();
        final var billingAmount = setting.getBillingAmount();
        final var billingCycle = setting.getBillingCycle();

        final var now = new Date();
        final var timeDifferenceSec = (now.getTime() - carParkingStatus.getInStorageDate().getTime()) / 1000;
        // 计费周期
        var cycle = timeDifferenceSec / billingCycle;
        // 首个计费周期计费
        if (setting.getFirstCycleCanBilling() && timeDifferenceSec % billingCycle > 0) {
            ++cycle;
        }
        // 计算价格
        return billingAmount.multiply(BigDecimal.valueOf(cycle));
    }


    @Override

    public boolean feePayment(CarParkingStatus carParkingStatus, BigDecimal payAmount, ParkingCard parkingCard) {

        final var carInfo = carParkingStatus.getCarInfo();
        // 总计需支付金额
        final var amountToBePaid = calcAmountToPaid(carInfo);
        log.debug("总计需支付金额:{}", amountToBePaid);
        // 支付
        parkingCardDService.pay(carParkingStatus, parkingCard, payAmount);
        // 总计已支付金额
        final BigDecimal paidAmount = parkingCardDService.listPayLogByCarPackingStatus(carParkingStatus).stream()
                .map(ParkingCardAmountLogDO::getChangeAmount)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
        log.debug("总计已支付金额:{}", paidAmount);

        // 判断是否已经付清
        final var payOff = amountToBePaid.compareTo(paidAmount) >= 0;
        log.debug("是否已经付清:{}", payOff);

        if (payOff) {
            // 15分钟内出库
            final var outStorageToken = "outStorage/" + carInfo.getId();
            stringRedisTemplate.opsForValue().setIfAbsent(outStorageToken, carInfo.getNumberPlate(), parkingSettingDService.getSetting().getBillingCycle(), TimeUnit.SECONDS);
            log.debug("添加出库凭证[{}]，{}{}内有效", outStorageToken, parkingSettingDService.getSetting().getBillingCycle(), TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    @Override
    public boolean outStorage(CarInfo carInfo) {
        final var outStorageToken = stringRedisTemplate.opsForValue().getAndDelete("outStorage/" + carInfo);

        carParkingStatusDService.deleteStorageStatus(carInfo, new DateTime());
        return Objects.equals(carInfo.getNumberPlate(), outStorageToken);
    }

    @Override
    public boolean outStorage(String numberPlate) {
        final var carInfo = carInfoDService.findByNumberPlate(numberPlate)
                .orElseThrow(() -> new BusinessException(StrFormatter.format("找不到车牌号为[{}]的车辆信息", numberPlate)));
        return outStorage(carInfo);
    }


}
