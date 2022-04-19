package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;
import run.bequick.dreamccc.pats.repository.ParkingCardRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ParkingCardDServiceImpl implements ParkingCardDService {
    private final ParkingCardRepository repository;
    private final ParkingCardAmountLogDODService logDODService;
    private final ParkingSettingDService settingDService;

    @Override
    public ParkingCard save(@Validated ParkingCard parkingCard) {
        return repository.save(parkingCard);
    }

    @Override
    public List<ParkingCard> listAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ParkingCard> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ParkingCard> findByCardNo(String cardNo) {
        return repository.getByCardNo(cardNo);
    }

    @Override
    public List<ParkingCardAmountLogDO> listPayLogByCarPackingStatus(CarParkingStatus carParkingStatus) {

        return logDODService.listByCarPackingStatusId(carParkingStatus.getId());
    }

    @Override

    public ParkingCard pay(CarParkingStatus carParkingStatus, ParkingCard parkingCard, BigDecimal amount) {
        final var setting = settingDService.getSetting();
        switch (parkingCard.getType()) {
            case DISPOSABLE:
            case USER_PERSISTENCE:
                final BigDecimal original = parkingCard.getAmount();
                // 精确减法
                final BigDecimal result = original.subtract(amount);
                if (result.signum() == -1) {
                    throw new BusinessException("停车卡余额不足");
                }
                parkingCard.setAmount(result);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.CONSUME, parkingCard,
                        carParkingStatus.getId().toString(), original, amount, result);
                return repository.save(parkingCard);
            case COUNT:
                var count = parkingCard.getAmount().intValue();
                if (amount.compareTo(setting.getMaxCountCardCanAmount()) > 0) {
                    throw new BusinessException("停车时长超过次卡可支付的最大金额");
                }
                if (count <= 0) {
                    throw new BusinessException("次卡余额不足，无法支付");
                }
                final var countResult = BigDecimal.valueOf(count - 1, 0);
                parkingCard.setAmount(countResult);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.CONSUME, parkingCard,
                        carParkingStatus.getId().toString(), parkingCard.getAmount(), amount, countResult);
                return repository.save(parkingCard);
            default:
                throw new BusinessException("未知的停车卡类型");
        }
    }

    @Override
    public ParkingCard recharge(String orderNum, ParkingCard parkingCard, BigDecimal amount) {
        switch (parkingCard.getType()) {
            case DISPOSABLE:
                throw new BusinessException("一次性停车卡不支持充值");
            case COUNT:
                final var countCardAmountMap = settingDService.getSetting().getCountCardAmountMap();
                for (Map.Entry<Integer, BigDecimal> entry : countCardAmountMap.entrySet()) {
                    Integer rechargeCount = entry.getKey();
                    BigDecimal rechargeAmount = entry.getValue();
                    if (rechargeAmount.compareTo(amount) == 0) {
                        final var original = parkingCard.getAmount();
                        final var result = original.add(BigDecimal.valueOf(rechargeCount));
                        parkingCard.setAmount(result);
                        logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.RECHARGE, parkingCard,
                                orderNum, original, amount, result);
                        return repository.save(parkingCard);
                    }
                }
                throw new BusinessException("充值失败，未找到该金额的充值规则");
            case USER_PERSISTENCE:
                final BigDecimal original = parkingCard.getAmount();
                final BigDecimal result = original.add(amount);
                parkingCard.setAmount(result);
                logDODService.addAmountLog(ParkingCardAmountLogDO.AmountChangeType.RECHARGE, parkingCard,
                        orderNum, original, amount, result);
                return repository.save(parkingCard);
            default:
                throw new BusinessException("未知的停车卡类型");
        }
    }

    @Override
    public ParkingCard bindCustomer(ParkingCard parkingCard, Customer customer) {

        if (Objects.nonNull(customer.getParkingCards())) {
            customer.getParkingCards().add(parkingCard);
        } else {
            customer.setParkingCards(Collections.singletonList(parkingCard));
        }
        parkingCard.setCustomer(customer);
        repository.save(parkingCard);
        return parkingCard;
    }
}
