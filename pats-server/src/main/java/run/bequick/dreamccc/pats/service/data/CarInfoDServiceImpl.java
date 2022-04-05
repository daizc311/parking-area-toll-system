package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.repository.CarInfoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarInfoDServiceImpl implements CarInfoDService {
    private final CarInfoRepository repository;

    @Override
    public List<CarInfo> listAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CarInfo> getById(@Validated @NotNull Long id) {

        var entity = repository.getById(id);
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<CarInfo> getByNumberPlate(@Validated @NotEmpty String numberPlate) {

        var entity = repository.getByNumberPlate(numberPlate);
        return Optional.of(entity);
    }

    @Override
    public CarInfo saveCarInfo(@Validated CarInfo entity) {

        return repository.save(entity);
    }
}
