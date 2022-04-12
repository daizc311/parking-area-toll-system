package run.bequick.dreamccc.pats.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.security.JwtLoginAuthenticationFilter;
import run.bequick.dreamccc.pats.security.SecurityConstant;

/**
 * 此Controller仅用于给Open-API扫描
 * <p>实际功能由JwtLoginAuthenticationFilter承担</p>
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class FakedLoginController {

    @Operation(summary = "后台登录", tags = {"Login"})
    @PostMapping(value = SecurityConstant.LOGIN_PATH)
    public JwtLoginAuthenticationFilter.TokenResponse login(@RequestParam String username, @RequestParam String password) {
        return null;
    }
}
