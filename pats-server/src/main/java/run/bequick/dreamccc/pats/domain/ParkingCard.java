package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @Schema(name = "卡内余额")
    private BigDecimal amount;

    @Schema(name = "与停车卡关联的客户信息")
    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

}
