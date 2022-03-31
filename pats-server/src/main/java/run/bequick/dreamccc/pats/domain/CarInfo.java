package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.util.Date;

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

public class CarInfo extends AbstractAuditable<AppUser, Long> {

    @Schema(name = "与车辆关联的客户信息")
    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    @Schema(name = "车牌号")
    @Column(name = "number_plate",unique = true,nullable = false)
    private String numberPlate;

    @Schema(name = "车辆型号")
    private String modelName;
}
