package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * <h3>停车卡充值/消费记录-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "parking_card_amonut_log", indexes = {
        @Index(name = "idx_pcal_userId", columnList = "userId"),
        @Index(name = "idx_pcal_cardId", columnList = "cardId"),
})
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ParkingCardAmountLogDO extends AbstractAuditable<AppUser,Long> {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "停车卡ID")
    private Long cardId;

    @Schema(description = "变动事件Id:充值时为订单Id,消费时为停车记录Id")
    private Long changeEventId;

    @Schema(description = "变动前余额")
    private BigDecimal beforeAmount;

    @Schema(description = "变动金额")
    private BigDecimal changeAmount;

    @Schema(description = "变动类型：充值/消费")
    private AmountChangeType type;

    @Schema(description = "变动后余额")
    private BigDecimal afterAmount;

    public enum AmountChangeType {
        /**
         * 充值
         */
        RECHARGE,
        /**
         * 消费
         */
        CONSUME
    }
}
