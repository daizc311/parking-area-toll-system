package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

/**
 * <h3>车辆停车记录-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "car_parking_log", indexes = {
})
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CarParkingLogDO extends AbstractAuditable<AppUser, Long> {

    @Schema(name = "[客户信息]Id")
    private Long customerId;

    @Schema(name = "[客户信息]真实姓名")
    private String customerRealName;

    @Schema(name = "[车辆信息]Id")
    private Long carId;

    @Schema(name = "[车辆信息]车牌号")
    private String carNumberPlate;

    @Schema(name = "[车辆信息]车辆型号")
    private String carModelName;

    @Schema(name = "[在库状态]Id")
    @Column(nullable = false)
    private Long statusId;

    @Schema(name = "出/入库类型")
    private CarParkingType type;

    @Schema(name = "出/入库时间")
    private Date parkingDate;

    public enum CarParkingType {
        IN,OUT
    }
}
