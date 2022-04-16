package run.bequick.dreamccc.pats.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordParam {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")
    private String newPassword;
}
