package run.bequick.dreamccc.pats.security;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import run.bequick.dreamccc.pats.common.DrResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class JwtLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 限制登录次数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("用户[{}]尝试登录", username);

        return authenticationManager.authenticate(new JwtUsernamePasswordAuthenticationToken(UserType.APP_USER,username, password));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        String username = request.getParameter("username");
        log.info("用户[{}]登录失败，cause:{}", username, failed.getMessage());
        DrResponse<Object> drResponse = DrResponse.failed();
        drResponse.setMessage(failed.getMessage());
        drResponse.setStatus(100);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        objectMapper.writeValue(response.getOutputStream(), drResponse);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        int expiresSecond = 1800;
        int refreshSecond = 3600;
        DateTime dateTime = new DateTime();
        Calendar accessTokenTime = dateTime.toCalendar();
        accessTokenTime.add(Calendar.SECOND, expiresSecond);
        Calendar refreshTokenTime = dateTime.toCalendar();
        refreshTokenTime.add(Calendar.SECOND, refreshSecond);

        // 成功认证刷新Token
        log.info("签发Token");
        JwtUserDetail userDetail = (JwtUserDetail) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstant.SECRET);
        String accessToken = JWT.create()
                .withSubject(userDetail.getUsername())
                .withExpiresAt(accessTokenTime.getTime())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("userType", UserType.APP_USER.toString())
                .withClaim("userId",userDetail.getUserId())
                .withClaim("roles", userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(userDetail.getUsername())
                .withExpiresAt(refreshTokenTime.getTime())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("userType",  UserType.APP_USER.toString())
                .withClaim("userId",userDetail.getUserId())
                .withClaim("roles", userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);

        response.setContentType(APPLICATION_JSON_VALUE);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccountId(userDetail.getUserId() + "");
        tokenResponse.setCreatedAt(dateTime);
        tokenResponse.setExpiresIn(expiresSecond);
        tokenResponse.setTokenType("bearer");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        objectMapper.writeValue(response.getOutputStream(), tokenResponse);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("created_at")
        private Date createdAt;
        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("account_id")
        private String accountId;
    }
}
