package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;


@Repository
public interface CarParkingStatusRepository extends JpaRepository<CarParkingStatus,Long> {

    CarParkingStatus findByCarInfo(CarInfo carInfo);
}
