package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.ParkingCardAmountLogDORepository;

@Service
@RequiredArgsConstructor
public class ParkingCardAmountLogDODServiceImpl implements ParkingCardAmountLogDODService{
    private final ParkingCardAmountLogDORepository repository;
}
