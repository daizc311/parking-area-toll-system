package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class RechargeParam {

    @Schema(description = "充值金额", example = "100")
    @NotNull
    @Min(1)
    private BigDecimal amount;

    @Schema(description = "停车卡Id", example = "10")
    @NotNull
    private Long parkingCardId;

    @NotEmpty
    @Schema(description = "外部订单号", example = "ORD-388582921205673")
    private String orderNum;
}
