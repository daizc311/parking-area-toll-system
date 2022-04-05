package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.CarInfo;

@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo,Long> {

    CarInfo findCarInfoByNumberPlate(String numberPlate);

    CarInfo findByNumberPlateLike(String numberPlate);

}
