package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.repository.CarInfoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    public Optional<CarInfo> findById(@Validated @NotNull Long id) {

        var entity = repository.getById(id);
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<CarInfo> findByNumberPlate(@Validated @NotEmpty String numberPlate) {

        var entity = repository.getByNumberPlate(numberPlate);
        return Optional.ofNullable(entity);
    }

    @Override
    @ServiceLog(value = "保存车辆信息 - {pos} - numberPlate:{}", paramEl = {"#root[0].numberPlate"})
    public CarInfo saveCarInfo(@Validated CarInfo entity) {

        return repository.save(entity);
    }

    @Override
    public CarInfo bindCustomer(CarInfo carInfo, Customer customer) {

        if (Objects.isNull(customer.getCarInfos())) {
            customer.setCarInfos(Collections.singletonList(carInfo));
        } else {
            customer.getCarInfos().add(carInfo);
        }
        carInfo.setCustomer(customer);

        return repository.save(carInfo);
    }
}
