package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

public interface CarParkingStatusDService {

    @Transactional
    void addStorageStatus(CarInfo carInfo, Date inStorageTime);

    @Transactional
    void deleteStorageStatus(CarInfo carInfo, Date outStorageTime);

    Optional<CarParkingStatus> findByCarInfo(CarInfo carInfo);
}
