package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.ParkingCard;

import java.math.BigDecimal;
import java.util.Optional;

public interface ParkingCardDService {

    ParkingCard save(ParkingCard parkingCard);

    Optional<ParkingCard> getByCardNo(String cardNo);

    ParkingCard pay(ParkingCard parkingCard, BigDecimal amount);

    ParkingCard recharge(ParkingCard parkingCard, BigDecimal amount);
}
