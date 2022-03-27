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
 * <h3>停车卡-数据库实体</h3>
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

    @Schema(name = "停车卡类型")
    private ParkingCardType type;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Customer customer;

    @Schema(name = "消费/充值记录")
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ParkingCardAmountLogDO.class)
    private List<ParkingCardAmountLogDO> amountLogs;

    @Schema(name = "有效期开始时间")
    private Date validityBeginTime;

    @Schema(name = "有效期开始时间")
    private Date validityEndTime;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

    private enum ParkingCardType {

        RECHARGEABLE("可充值型", "永久停车卡"),
        NOT_RECHARGEABLE("不可充值型", "一次性停车卡"),
        FREE_LIMIT_TIME("限时免费型", "限时免费卡");

        ParkingCardType(String innerName, String displayName) {
            this.innerName = innerName;
            this.displayName = displayName;
        }

        private final String innerName;
        private final String displayName;
    }
}
