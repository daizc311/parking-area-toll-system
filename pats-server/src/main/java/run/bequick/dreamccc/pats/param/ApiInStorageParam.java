package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ApiInStorageParam {

    @NotNull
    private SimpleCarInfo carInfo;

    @NotNull
    private Date inStorageTime;

    @Data
    static class SimpleCarInfo {

        @NotEmpty
        @Schema(name = "车牌号")
        private String numberPlate;

        @Schema(name = "车辆型号")
        private String modelName;

        @Schema(name = "车辆颜色")
        private String color;
    }
}
