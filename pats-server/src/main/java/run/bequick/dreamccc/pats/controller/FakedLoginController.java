package run.bequick.dreamccc.pats.controller;

import org.springframework.web.bind.annotation.*;
import run.bequick.dreamccc.pats.security.JwtLoginAuthenticationFilter;
import run.bequick.dreamccc.pats.security.SecurityConstant;

import static org.springframework.http.HttpStatus.OK;

/**
 * 此Controller仅用于给Open-API扫描
 * <p>实际功能由JwtLoginAuthenticationFilter承担</p>
 */
@RestController
@RequestMapping
public class FakedLoginController {

    @GetMapping(SecurityConstant.LOGIN_PATH)
    @ResponseStatus(OK)
    public JwtLoginAuthenticationFilter.TokenResponse login(@RequestParam String username, @RequestParam String password) {
        return null;
    }
}
