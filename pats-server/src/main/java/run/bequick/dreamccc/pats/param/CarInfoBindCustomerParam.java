package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CarInfoBindCustomerParam {

    @Schema(description = "车辆信息Id", example = "10")
    @NotNull
    private Long carInfoId;

    @Schema(description = "客户Id", example = "10")
    @NotNull
    private Long customerId;
}
