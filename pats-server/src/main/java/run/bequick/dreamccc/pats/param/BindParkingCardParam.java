package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class BindParkingCardParam {

    @Schema(name = "停车卡号")
    @NotBlank
    private String cardNo;

    @Schema(name = "停车卡密码")
    @NotBlank
    private String cardPwd;
}
