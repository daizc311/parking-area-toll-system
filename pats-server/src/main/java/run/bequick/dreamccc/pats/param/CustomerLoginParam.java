package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerLoginParam {

    @Schema(description = "登录名称", example = "user666")
    @NotBlank
    private String loginName;

    @Schema(description = "登录密码", example = "user666")
    @NotBlank
    private String password;

}
