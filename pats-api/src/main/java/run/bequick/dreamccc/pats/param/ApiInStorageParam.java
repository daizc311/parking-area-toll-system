package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Schema(title = "入库所需的参数")
public class ApiInStorageParam {

    @NotNull
    @Schema(description = "入库时的车辆信息", required = true)
    private SimpleCarInfo carInfo;

    @NotNull
    @Schema(description = "入库时的时间", required = true)
    private Date inStorageTime;

    @Data
    static class SimpleCarInfo {

        @NotEmpty
        @Schema(description = "车牌号", required = true)
        private String numberPlate;

        @Schema(description = "车辆型号")
        private String modelName;

        @Schema(description = "车辆颜色")
        private String color;
    }
}
