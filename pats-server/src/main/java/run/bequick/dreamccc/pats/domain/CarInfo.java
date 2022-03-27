package run.bequick.dreamccc.pats.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.scheduling.annotation.Schedules;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * <h3>车辆信息-数据库实体</h3>
 *
 * @author Daizc
 */
@Data
@Table(name = "car_info", indexes = {
        @Index(name = "idx_numberPlate", columnList = "numberPlate",unique = true)
})
@Entity
@NoArgsConstructor

public class CarInfo {

    @Id
    private Long id;

    @Schema(name = "车牌号")
    private String numberPlate;

    @Schema(name = "车辆型号")
    private String modelName;

    @CreatedDate
    private Date createTime;
}
