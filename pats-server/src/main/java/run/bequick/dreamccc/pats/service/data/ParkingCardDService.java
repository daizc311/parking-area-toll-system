package run.bequick.dreamccc.pats.service.data;

import run.bequick.dreamccc.pats.domain.ParkingCard;

import java.util.Optional;

public interface ParkingCardDService {
    Optional<ParkingCard> getByCardNo(String cardNo);
}
