package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.repository.CarInfo3Repository;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarInfoDServiceImpl implements CarInfoDService {
    private final CarInfo3Repository repository;

    public List<CarInfo> listAll() {
        return repository.findAll();
    }

    public Optional<CarInfo> getById(@Validated @NotNull Long id) {

        try {
            var entity = repository.getById(id);
            return Optional.of(entity);
        } catch (EntityNotFoundException entityNotFoundException) {
            return Optional.empty();
        }
    }

    public CarInfo save(@Validated CarInfo entity) {

        return repository.save(entity);
    }

    public CarInfo update()
}
