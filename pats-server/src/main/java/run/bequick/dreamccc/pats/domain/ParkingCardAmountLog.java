package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <h3>停车卡充值/消费记录</h3>
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
public class ParkingCardAmountLog {

    @Id
    private Long id;

    private Long cardId;

    private BigDecimal beforeAmount;

    private AmountChangeType type;

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
