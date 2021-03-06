package run.bequick.dreamccc.pats.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.bequick.dreamccc.pats.common.ColumnComment;
import run.bequick.dreamccc.pats.enums.ParkingCardTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * <h3>停车卡-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "parking_card")
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ParkingCard extends AbstractAuditable<AppUser, Long> {

    @Schema(description = "[展示/绑定时用]停车卡号")
    @Column(unique = true, nullable = false)
    @ColumnComment("停车卡号")
    @NotEmpty
    private String cardNo;

    @Schema(description = "[绑定时用]停车卡密码")
    @Column(nullable = false)
    @ColumnComment("停车卡密码")
    @NotEmpty
    private String cardPwd;

    @Schema(description = "停车卡类型")
    @Column(nullable = false)
    @ColumnComment("停车卡类型")
    @NotNull
    private ParkingCardTypeEnum type;

    @Schema(description = "卡内余额")
    @Column(nullable = false)
    @ColumnComment("卡内余额")
    @NotNull
    private BigDecimal amount;

    @JsonIgnoreProperties(value = "parkingCards")
    @Schema(description = "与停车卡关联的客户信息")
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ColumnComment("客户信息Id")
    private Customer customer;

}
