package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.CarParkingStatusRepository;

@Service
@RequiredArgsConstructor
public class CarParkingStatusDServiceImpl implements CarParkingStatusDService{
    private final CarParkingStatusRepository repository;
}
