package run.bequick.dreamccc.pats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;
import run.bequick.dreamccc.pats.service.data.CarInfoDService;
import run.bequick.dreamccc.pats.service.data.CarParkingStatusDService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InOutStorageServiceImpl implements InOutStorageService {

    private final CarParkingStatusDService carParkingStatusDService;
    private final CarInfoDService carInfoDService;

    @Override
    @Transactional
    @ServiceLog(value = "车辆入库 - {pos} - numberPlate:{}",paramEl = {"#root[0].carInfo.numberPlate"})
    public void inStorage(ApiInStorageParam param) {

        var simpleCarInfo = param.getCarInfo();
        var inStorageTime = param.getInStorageTime();
        var numberPlate = simpleCarInfo.getNumberPlate();

        CarInfo carInfo = carInfoDService.getByNumberPlate(numberPlate).orElseGet(() -> {

            var addCI = new CarInfo();
            addCI.setModelName(simpleCarInfo.getModelName());
            addCI.setNumberPlate(simpleCarInfo.getNumberPlate());
            addCI.setColor(simpleCarInfo.getColor());
            return carInfoDService.saveCarInfo(addCI);
        });

        carParkingStatusDService.addStorageStatus(carInfo, inStorageTime);
    }

    @Override
    public void feePayment(ApiFeePaymentParam param) {

    }

    @Override
    public void outStorage(ApiOutStorageParam param) {

        var paramNumberPlate = param.getNumberPlate();
        // TODO 检查是否可以出库

        // TODO 出库

    }
}
