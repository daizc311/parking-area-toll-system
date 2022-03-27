package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <h3>停车卡充值/消费记录-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "parking_card_amonut_log", indexes = {
        @Index(name = "idx_pcal_type", columnList = "type"),
        @Index(name = "idx_pcal_createTime", columnList = "createTime")
})
@Entity
@NoArgsConstructor
public class ParkingCardAmountLogDO {

    @Id
    private Long id;

    @Schema(name = "停车卡ID")
    private Long cardId;

    @Schema(name = "变动前余额")
    private BigDecimal beforeAmount;

    @Schema(name = "变动类型：充值/消费")
    private AmountChangeType type;

    @Schema(name = "变动后余额")
    private BigDecimal afterAmount;

    @CreatedDate
    private Date createTime;

    enum AmountChangeType {
        /**
         * 充值
         */
        RECHARGE,
        /**
         * 消费
         */
        CONSUME;
    }
}
