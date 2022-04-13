package run.bequick.dreamccc.pats.enums;


import lombok.Getter;

@Getter
public enum AppRoleEnum {

    ADMIN("超级管理员"),
    //    USER_ADMIN("用户管理员，有和超级管理员"),
    GARAGE_MANAGER("车库管理员"),
    FINANCIAL_MANAGER("财务管理员"),
    CUSTOMER("客户");

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_GARAGE_MANAGER = "GARAGE_MANAGER";
    public static final String ROLE_FINANCIAL_MANAGER = "FINANCIAL_MANAGER";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    private final String name;
    private final String description;

    AppRoleEnum(String name, String description) {

        this.name = this.toString();
        this.description = description;
    }

    AppRoleEnum(String description) {

        this.name = this.toString();
        this.description = description;
    }
}
