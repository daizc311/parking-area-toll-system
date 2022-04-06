package run.bequick.dreamccc.pats.security;

import lombok.Getter;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.domain.Customer;

public enum UserType {
    APP_USER(AppUser.class), CUSTOMER(Customer.class);

    @Getter
    private final Class<?> aClass;

    UserType(Class<?> aClass) {
        this.aClass = aClass;
    }
}
