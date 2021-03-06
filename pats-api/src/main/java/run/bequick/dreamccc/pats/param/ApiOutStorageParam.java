package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ApiOutStorageParam {

    @NotEmpty
    @Schema(description = "车牌号", required = true)
    private String numberPlate;

}
