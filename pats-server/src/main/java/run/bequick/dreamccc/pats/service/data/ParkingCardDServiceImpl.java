package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.ParkingCardRepository;

@Service
@RequiredArgsConstructor
public class ParkingCardDServiceImpl implements ParkingCardDService{
    private final ParkingCardRepository repository;
}
