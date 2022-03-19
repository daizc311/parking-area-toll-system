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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 限制登录次数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("用户[{}]尝试登录", username);

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        int expiresSecond = 600;
        int refreshSecond = 3600;
        DateTime dateTime = new DateTime();
        Calendar accessTokenTime = dateTime.toCalendar();
        accessTokenTime.add(Calendar.SECOND,expiresSecond);
        Calendar refreshTokenTime = dateTime.toCalendar();
        refreshTokenTime.add(Calendar.SECOND,refreshSecond);

        // 成功认证刷新Token
        log.info("签发Token");
        User userDetail = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("HMAC256");
        String accessToken = JWT.create()
                .withSubject(userDetail.getUsername())
                .withExpiresAt(accessTokenTime.getTime())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(userDetail.getUsername())
                .withExpiresAt(refreshTokenTime.getTime())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("access_token",accessToken);
        response.setHeader("refresh_token",refreshToken);

        response.setContentType(APPLICATION_JSON_VALUE);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccountId(userDetail.getUsername());
        tokenResponse.setCreatedAt(dateTime);
        tokenResponse.setExpiresIn(expiresSecond);
        tokenResponse.setTokenType("bearer");
        objectMapper.writeValue(response.getOutputStream(),tokenResponse);
//        super.successfulAuthentication(request, response, chain, authentication);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TokenResponse{
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
