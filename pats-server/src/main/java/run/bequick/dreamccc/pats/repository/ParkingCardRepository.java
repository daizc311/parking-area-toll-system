package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.ParkingCard;

import java.util.Optional;

@Repository
public interface ParkingCardRepository extends JpaRepository<ParkingCard,Long> {

    Optional<ParkingCard> getByCardNo(String cardNo);
}
