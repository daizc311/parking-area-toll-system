package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * <h3>停车卡</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "parking_card")
@Entity
@NoArgsConstructor
public class ParkingCard {

    @Id
    private Long id;

    @Schema(name = "卡内余额")
    private BigDecimal amount;


    @Schema(name = "消费/充值记录")
    @OneToMany(fetch = FetchType.LAZY)
    private List<ParkingCardAmountLog> amountLogs;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
