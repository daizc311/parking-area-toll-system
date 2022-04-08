package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * <h3>车辆信息-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "car_info", indexes = {
})
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CarInfo extends AbstractAuditable<AppUser, Long> {

    @Schema(name = "与车辆关联的客户信息")
    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    @Schema(name = "车牌号")
    @Column(unique = true,nullable = false)
    @NotNull(message = "车牌号不能为空")
    private String numberPlate;

    @Schema(name = "车辆型号")
    private String modelName;

    @Schema(name = "车辆颜色")
    private String color;
}
