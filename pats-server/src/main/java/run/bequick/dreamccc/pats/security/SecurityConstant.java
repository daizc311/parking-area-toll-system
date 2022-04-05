package run.bequick.dreamccc.pats.security;

import run.bequick.dreamccc.pats.domain.AppRole;

import java.util.Map;

public class SecurityConstant {

    public static final String SECRET = "HMAC256";

    public static final String BEARER = "Bearer ";

    public static final String LOGIN_PATH = "/login";

    public static final AppRole ROLE_CUSTOMER = new AppRole("CUSTOMER");
}
