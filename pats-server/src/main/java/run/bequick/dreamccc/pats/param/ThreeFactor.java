package run.bequick.dreamccc.pats.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(name = "个人三要素")
public class ThreeFactor {
    @Schema(name = "[三要素之一]真实姓名",required = true)
    @NotEmpty
    private String realName;

    @Schema(name = "[三要素之一]身份证号",required = true)
    @NotEmpty
    private String idNumber;

    @Schema(name = "[三要素之一][可用于登录]手机号",required = true)
    @NotEmpty
    private String mobile;
}
