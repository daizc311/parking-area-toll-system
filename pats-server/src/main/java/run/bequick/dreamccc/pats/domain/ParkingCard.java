package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
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
public class ParkingCard extends AbstractAuditable<AppUser,Long> {

    @Schema(name = "[展示/绑定时用]停车卡号")
    @Column(unique = true,nullable = false)
    private String cardNo;

    @Schema(name = "[绑定时用]停车卡密码")
    @Column(nullable = false)
    private String cardPwd;

    @Schema(name = "卡内余额")
    private BigDecimal amount;

    @Schema(name = "与停车卡关联的客户信息")
    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

}
