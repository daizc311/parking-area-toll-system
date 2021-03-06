package run.bequick.dreamccc.pats.security;

import cn.hutool.core.text.StrFormatter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter implements Ordered {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtTokenStr = null;
            if (request.getServletPath().equals(LOGIN_PATH)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (null != authHeader && authHeader.length() > 6 && authHeader.substring(0, BEARER.length()).equalsIgnoreCase(BEARER)) {
                jwtTokenStr = authHeader.substring(BEARER.length());
            }
            final String authParameter = request.getParameter("token");
            if (Strings.isNotBlank(authParameter)) {
                jwtTokenStr = authParameter;
            }

            if (Strings.isNotBlank(jwtTokenStr)) {
                Algorithm algorithm = Algorithm.HMAC256(SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT jwt = jwtVerifier.verify(jwtTokenStr);
                String username = jwt.getSubject();
                String[] roles = jwt.getClaim("roles").asArray(String.class);
                Long userId = jwt.getClaim("userId").asLong();
                String type = jwt.getClaim("userType").asString();
                var grantedAuthorityList = Stream.of(roles)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                var authenticationToken = new JwtUsernamePasswordAuthenticationToken(
                        UserType.valueOf(type),
                        userId, username, null, grantedAuthorityList);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
//            if (Strings.isBlank(jwtTokenStr)) {
//                throw new JWTVerificationException("?????????token");
//            }
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            final var failed = DrResponse.failed(e.getMessage());
            if (e instanceof TokenExpiredException) {
                // ??????
                failed.setStatus(101);
                failed.setMessage("???????????????????????????");
            }
            objectMapper.writeValue(response.getOutputStream(), failed);
            log.info(StrFormatter.format("Token????????????:{}", e.getMessage()));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
