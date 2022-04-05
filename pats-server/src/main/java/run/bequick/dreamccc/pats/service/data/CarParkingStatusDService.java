package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.CarInfo;

import javax.transaction.Transactional;
import java.util.Date;

public interface CarParkingStatusDService {

    @Transactional
    void addStorageStatus(CarInfo carInfo, Date inStorageTime);

    @Transactional
    void deleteStorageStatus(CarInfo carInfo, Date outStorageTime);
}
