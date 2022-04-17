package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;

import java.util.Date;
import java.util.Optional;

public interface CarParkingStatusDService {


    void addStorageStatus(CarInfo carInfo, Date inStorageTime);


    void deleteStorageStatus(CarInfo carInfo, Date outStorageTime);

    Optional<CarParkingStatus> findByCarInfo(CarInfo carInfo);
}
