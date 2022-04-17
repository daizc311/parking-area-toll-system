package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.CarParkingLogDO;

@Repository
public interface CarParkingLogDORepository extends JpaRepository<CarParkingLogDO, Long> {
//
//    @Override
//    List<CarParkingLogDO> findByParkingDate
}
