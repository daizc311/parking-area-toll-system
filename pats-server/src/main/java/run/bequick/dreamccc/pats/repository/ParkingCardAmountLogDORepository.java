package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.ParkingCardAmountLogDO;

import java.util.List;

@Repository
public interface ParkingCardAmountLogDORepository extends JpaRepository<ParkingCardAmountLogDO, Long> {

    List<ParkingCardAmountLogDO> findAllByChangeEventIdAndType(String changeEventId, ParkingCardAmountLogDO.AmountChangeType type);
}
