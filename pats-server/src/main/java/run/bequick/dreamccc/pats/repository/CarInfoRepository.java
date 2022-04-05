package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.CarInfo;

import java.util.Optional;

@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo,Long> {

    CarInfo getByNumberPlate(String numberPlate);

    Optional<CarInfo> findByNumberPlateLike(String numberPlate);

}
