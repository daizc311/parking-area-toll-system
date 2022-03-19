package run.bequick.dreamccc.pats.security;

import cn.hutool.core.text.StrFormatter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import run.bequick.dreamccc.pats.common.DrResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static run.bequick.dreamccc.pats.security.SecurityConstant.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(LOGIN_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNotBlank(authHeader) && authHeader.startsWith(BEARER)) {
            try {
                String jwtTokenStr = authHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT jwt = jwtVerifier.verify(jwtTokenStr);
                String username = jwt.getSubject();
                String[] roles = jwt.getClaim("roles").asArray(String.class);
                var grantedAuthorityList = Stream.of(roles)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorityList);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                objectMapper.writeValue(response.getOutputStream(),DrResponse.failed("登录失效，请重新登录"));
                log.info(StrFormatter.format("Token验证失败:{}", e.getMessage()));
            }
        }

    }
}
