package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ParkingCardBindCustomerParam {

    @Schema(description = "停车卡Id", example = "10")
    @NotNull
    private Long parkingCardId;

    @Schema(description = "客户Id", example = "10")
    @NotNull
    private Long customerId;
}
