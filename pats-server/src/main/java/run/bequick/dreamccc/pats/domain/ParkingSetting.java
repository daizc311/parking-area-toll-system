package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Table(name = "parking_setting")
@Entity
@NoArgsConstructor
public class ParkingSetting extends AbstractAuditable<AppUser,String> {

    @Schema(name = "计费周期(秒)")
    @Column(nullable = false)
    @NotNull
    private Long billingCycle;

    @Schema(name = "计费金额(精确到小数后两位)")
    @Column(nullable = false,precision = 2)
    @NotNull
    private BigDecimal billingAmount;

    @Schema(name = "车位总数")
    @Column(nullable = false)
    @Min(1)
    private Integer parkingSpacesTotal;
}
