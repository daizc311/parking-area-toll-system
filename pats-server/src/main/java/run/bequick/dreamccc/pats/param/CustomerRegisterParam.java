package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerRegisterParam {

    @Schema(description = "三要素认证结果字符串")
    @NotBlank
    private String threeFactor;

    @Schema(description = "登录名称", example = "user666")
    @NotBlank
    private String loginName;

    @Schema(description = "登录密码", example = "user666")
    @NotBlank
    private String password;
}
