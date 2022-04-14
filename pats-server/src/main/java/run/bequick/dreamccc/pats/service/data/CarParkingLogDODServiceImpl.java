package run.bequick.dreamccc.pats.service.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.CarParkingLogDO;
import run.bequick.dreamccc.pats.domain.CarParkingStatus;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.repository.CarParkingLogDORepository;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
@Slf4j
@Service
@RequiredArgsConstructor
public class CarParkingLogDODServiceImpl implements CarParkingLogDODService {

    private final CarParkingLogDORepository repository;

    @Transactional
    @Override
    @ServiceLog(value = "添加出入库记录 - {pos} - 方向:{},carId:{}", paramEl = {"#root[0]", "#root[1].carInfo.id"})
    public void addStorageLog(@Validated @NotNull CarParkingLogDO.CarParkingType type, CarParkingStatus carParkingStatus) {
        var carInfo = carParkingStatus.getCarInfo();
        var addInLog = new CarParkingLogDO();

        addInLog.setCarId(carParkingStatus.getCarInfo().getId());
        addInLog.setCarModelName(carParkingStatus.getCarInfo().getModelName());
        addInLog.setCarNumberPlate(carParkingStatus.getCarInfo().getNumberPlate());
        Customer carInfoCustomer = carInfo.getCustomer();
        if (Objects.nonNull(carInfoCustomer)) {
            addInLog.setCustomerId(carInfo.getId());
            addInLog.setCustomerRealName(carInfoCustomer.getRealName());
        }
        addInLog.setParkingDate(carParkingStatus.getInStorageDate());
        addInLog.setStatusId(carParkingStatus.getId());
        addInLog.setType(type);
        repository.save(addInLog);
    }
}
