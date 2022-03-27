package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * <h3>车辆停车记录-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "car_parking_log", indexes = {
        @Index(name = "idx_cpl_parkingBeginTime", columnList = "parkingBeginTime"),
        @Index(name = "idx_cpl_parkingEndTime", columnList = "parkingEndTime")
})
@Entity
@NoArgsConstructor
public class CarParkingLogDO {

    @Id
    private Long id;

    @Schema(name = "[客户信息]Id")
    private String customerId;

    @Schema(name = "[客户信息]真实姓名")
    private String customerRealName;

    @Schema(name = "[车辆信息]Id")
    private String carId;

    @Schema(name = "[车辆信息]车牌号")
    private String carNumberPlate;

    @Schema(name = "[车辆信息]车辆型号")
    private String carModelName;

    @Schema(name = "入库时间")
    private Date parkingBeginTime;

    @Schema(name = "出库时间")
    private Date parkingEndTime;

    @CreatedDate
    private Date createTime;
}
