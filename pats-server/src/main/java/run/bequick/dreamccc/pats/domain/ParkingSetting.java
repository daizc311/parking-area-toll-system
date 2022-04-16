package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.bequick.dreamccc.pats.common.PayPriceConverter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Table(name = "parking_setting")
@Entity
@NoArgsConstructor
public class ParkingSetting {

    @Id
    private String id;

    @Schema(description = "系统名称,会展示在登录页和title上")
    @Column(nullable = false)
    @NotEmpty
    private String systemName;

    @Schema(description = "计费周期(秒)", minimum = "60", maximum = "86400")
    @Column(nullable = false)
    @NotNull
    @Min(60)
    @Max(86400)
    private Long billingCycle;

    @Schema(description = "计费金额(精确到小数后两位)", minimum = "0", maximum = "1000")
    @Column(nullable = false, precision = 2)
    @NotNull
    @Min(0)
    @Max(1000)
    private BigDecimal billingAmount;

    @Schema(description = "次卡允许支付的最大金额(精确到小数后两位)", minimum = "0", maximum = "1000")
    @Column(nullable = false, precision = 2)
    @NotNull
    @Min(0)
    @Max(1000)
    private BigDecimal maxCountCardCanAmount;

    @Schema(description = "次卡允许支付的最大金额(精确到小数后两位)", minimum = "0", maximum = "1000")
    @Column(nullable = false, precision = 2, columnDefinition = "json")
    @NotNull
    @Convert(converter = PayPriceConverter.class)
    private Map<Integer, BigDecimal> countCardAmountMap;

    @Schema(description = "车位总数")
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer parkingSpacesTotal;

    @Schema(description = "首个计费周期是否计费")
    @Column(nullable = false)
    @NotNull
    private Boolean firstCycleCanBilling;

}
