package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.domain.ParkingCard;
import run.bequick.dreamccc.pats.repository.ParkingCardRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingCardDServiceImpl implements ParkingCardDService{
    private final ParkingCardRepository repository;

    @Override
    public Optional<ParkingCard> getByCardNo(String cardNo) {
        return repository.getByCardNo(cardNo);
    }
}
