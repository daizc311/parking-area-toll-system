package run.bequick.dreamccc.pats.service.data;

import org.springframework.transaction.annotation.Transactional;
import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;

public interface CarParkingLogDODService {

    @Transactional
    void addStorageLog(CarParkingLogDO.CarParkingType type, CarParkingStatus carParkingStatus);
}
