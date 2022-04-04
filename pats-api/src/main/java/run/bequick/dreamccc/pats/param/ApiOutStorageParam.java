package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ApiOutStorageParam {

    @NotEmpty
    @Schema(name = "车牌号")
    private String numberPlate;

}
