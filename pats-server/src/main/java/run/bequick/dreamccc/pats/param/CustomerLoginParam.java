package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerLoginParam {

    @Schema(name = "登录名称")
    @NotBlank
    private String loginName;

    @Schema(name = "登录密码")
    @NotBlank
    private String password;

}
