package run.bequick.dreamccc.pats.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LinkRole2UserParam {
    @NotBlank(message = "用户ID不能为空")
    private Long userId;
    @NotBlank(message = "角色ID不能为空")
    private Long roleId;
}