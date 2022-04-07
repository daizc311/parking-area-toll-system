package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.ParkingSetting;

@Repository
public interface ParkingSettingRepository extends JpaRepository<ParkingSetting,String> {

}
