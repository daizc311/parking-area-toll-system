package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.util.Date;

/**
 * <h3>车辆停车状态-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "car_parking_status", indexes = {})
@Entity
@NoArgsConstructor
public class CarParkingStatusDO extends AbstractAuditable<AppUser, Long> {

    @OneToOne
    @JoinColumn(name = "car_info_id", referencedColumnName = "id")
    @Schema(name = "车辆信息")
    private CarInfo carInfo;

    @Schema(name = "车辆入库时间")
    private Date inStorageDate;
}
