package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ApiOutStorageParam {

    @Schema(name = "支付信息")
    private PaymentInfo paymentInfo;

    @NotEmpty
    @Schema(name = "车牌号")
    private String numberPlate;

    @NotNull
    @Schema(name = "出库时间")
    private Date outStorageTime;

    @Data
    static class PaymentInfo {

        @Schema(name = "支付方式")
        private String method = "未知";

        @Min(value = 0)
        @NotNull
        @Schema(name = "支付金额")
        private BigDecimal amount;
    }
}
