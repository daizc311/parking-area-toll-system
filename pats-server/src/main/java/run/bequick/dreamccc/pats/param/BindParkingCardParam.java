package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BindParkingCardParam {

    @Schema(description = "停车卡号")
    @NotBlank
    private String cardNo;

    @Schema(description = "停车卡密码")
    @NotBlank
    private String cardPwd;
}
