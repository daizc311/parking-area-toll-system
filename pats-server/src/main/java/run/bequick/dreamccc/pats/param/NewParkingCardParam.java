package run.bequick.dreamccc.pats.param;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import run.bequick.dreamccc.pats.enums.ParkingCardTypeEnum;

@Data
public class NewParkingCardParam {

    @NotNull
    @Schema(description = "停车卡类型", example = "COUNT")
    private ParkingCardTypeEnum type;
}
