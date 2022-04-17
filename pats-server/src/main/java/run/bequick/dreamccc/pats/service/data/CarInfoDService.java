package run.bequick.dreamccc.pats.service.data;

import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.domain.CarInfo;
import run.bequick.dreamccc.pats.domain.Customer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CarInfoDService {

    List<CarInfo> listAll();

    Optional<CarInfo> findById(@Validated @NotNull Long id);

    Optional<CarInfo> findByNumberPlate(@Validated @NotEmpty String numberPlate);

    CarInfo saveCarInfo(@Validated CarInfo entity);

    CarInfo bindCustomer(CarInfo carInfo, Customer customer);
}
