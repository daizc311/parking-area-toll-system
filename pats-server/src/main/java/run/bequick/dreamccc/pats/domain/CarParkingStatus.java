package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <h3>车辆停车状态-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "car_parking_status", indexes = {
        @Index(name = "idx_cps_car_info_id", columnList = "car_info_id", unique = true)
})
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CarParkingStatus extends AbstractAuditable<AppUser, Long> {

    @OneToOne
    @JoinColumn(name = "car_info_id", referencedColumnName = "id", unique = true)
    @Schema(description = "车辆信息")
    private CarInfo carInfo;

    @Schema(description = "车辆入库时间")
    private Date inStorageDate;
}
