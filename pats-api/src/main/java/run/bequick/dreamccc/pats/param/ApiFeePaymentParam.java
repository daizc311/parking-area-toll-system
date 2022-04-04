package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ApiFeePaymentParam {

    @Schema(name = "支付方式")
    private String method = "未知";

    @Min(value = 0)
    @NotNull
    @Schema(name = "支付金额")
    private BigDecimal amount;
}
