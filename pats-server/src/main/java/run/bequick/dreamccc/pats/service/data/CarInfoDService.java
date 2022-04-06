package run.bequick.dreamccc.pats.service.data;

import org.springframework.validation.annotation.Validated;
import run.bequick.dreamccc.pats.domain.CarInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CarInfoDService {

    List<CarInfo> listAll();

    Optional<CarInfo> getById(@Validated @NotNull Long id);

    Optional<CarInfo> getByNumberPlate(@Validated @NotEmpty String numberPlate);

    CarInfo saveCarInfo(@Validated CarInfo entity);

}
