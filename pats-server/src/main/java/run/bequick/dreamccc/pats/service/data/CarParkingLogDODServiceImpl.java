package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.CarParkingLogDORepository;

@Service
@RequiredArgsConstructor
public class CarParkingLogDODServiceImpl implements CarParkingLogDODService{
    private final CarParkingLogDORepository repository;
}
