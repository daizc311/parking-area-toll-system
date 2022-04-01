package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.repository.CarInfoRepository;

@Service
@RequiredArgsConstructor
public class CarInfoDServiceImpl implements CarInfoDService{
    private final CarInfoRepository repository;
}
