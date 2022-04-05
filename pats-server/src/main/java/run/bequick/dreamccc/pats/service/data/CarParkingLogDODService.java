package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.config.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;

import javax.transaction.Transactional;

public interface CarParkingLogDODService {

    void addStorageLog(CarParkingLogDO.CarParkingType type, CarParkingStatus carParkingStatus);
}
