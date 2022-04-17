package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;

public interface CarParkingLogDODService {


    void addStorageLog(CarParkingLogDO.CarParkingType type, CarParkingStatus carParkingStatus);
}
