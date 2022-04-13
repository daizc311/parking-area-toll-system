package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LinkRole2UserParam {

    @Schema(description = "用户Id", example = "10")
    @NotBlank(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "角色", example = "ADMIN")
    @NotBlank(message = "角色不能为空")
    private String role;
}