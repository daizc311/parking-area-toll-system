package run.bequick.dreamccc.pats.enums;


import lombok.Getter;

@Getter
public enum AppRoleEnum {

    ADMIN("超级管理员"),
    //    USER_ADMIN("用户管理员，有和超级管理员"),
    GARAGE_MANAGER("车库管理员"),
    FINANCIAL_MANAGER("财务管理员"),
    CUSTOMER("客户");

    private final String description;

    AppRoleEnum(String description) {
        this.description = description;
    }
}
