package run.bequick.dreamccc.pats.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.bequick.dreamccc.pats.common.ColumnComment;

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

    @Schema(description = "与车辆关联的客户信息")
    @ManyToOne
    @JsonIgnoreProperties(value = "carInfos")
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ColumnComment("客户信息Id")
    private Customer customer;

    @Schema(description = "车牌号")
    @Column(unique = true, nullable = false)
    @ColumnComment("车牌号")
    @NotNull(message = "车牌号不能为空")
    private String numberPlate;

    @Schema(description = "车辆型号")
    @ColumnComment("车辆型号")
    private String modelName;

    @Schema(description = "车辆颜色")
    @ColumnComment("车辆颜色")
    private String color;
}
