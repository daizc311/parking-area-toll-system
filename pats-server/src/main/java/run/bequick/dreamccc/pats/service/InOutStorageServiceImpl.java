package run.bequick.dreamccc.pats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.param.ApiFeePaymentParam;
import run.bequick.dreamccc.pats.param.ApiInStorageParam;
import run.bequick.dreamccc.pats.param.ApiOutStorageParam;
import run.bequick.dreamccc.pats.repository.CarInfoRepository;
import run.bequick.dreamccc.pats.repository.CarParkingLogDORepository;
import run.bequick.dreamccc.pats.repository.CarParkingStatusRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class InOutStorageServiceImpl implements InOutStorageService {

    private final CarParkingLogDORepository carParkingLogDORepository;
    private final CarParkingStatusRepository carParkingStatusRepository;
    private final CarInfoRepository carInfoRepository;

    @Override
    public boolean inStorage(ApiInStorageParam param) {

        var simpleCarInfo = param.getCarInfo();
        var inStorageTime = param.getInStorageTime();

        String numberPlate = simpleCarInfo.getNumberPlate();

        CarInfo carInfo;
        try {
            carInfo = carInfoRepository.findCarInfoByNumberPlate(numberPlate);
        } catch (EntityNotFoundException e) {
            var addCI = new CarInfo();
            addCI.setModelName(simpleCarInfo.getModelName());
            addCI.setNumberPlate(simpleCarInfo.getNumberPlate());
            addCI.setColor(simpleCarInfo.getColor());
            carInfo = carInfoRepository.save(addCI);
        }



        return false;
    }

    @Override
    public boolean feePayment(ApiFeePaymentParam param) {
        return false;
    }

    @Override
    public boolean outStorage(ApiOutStorageParam param) {
        return false;
    }
}
