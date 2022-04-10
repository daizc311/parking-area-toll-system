package run.bequick.dreamccc.pats.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        var loginFilter = new JwtLoginAuthenticationFilter(authenticationManager(), objectMapper);
        loginFilter.setFilterProcessesUrl(SecurityConstant.LOGIN_PATH);
        var jwtAuthorizationFilter = new JwtAuthorizationFilter(objectMapper);

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .authorizeRequests().requestMatchers(new AntPathRequestMatcher("/setting/getSetting")).permitAll()
                .and()
                .authorizeRequests().requestMatchers(new AntPathRequestMatcher("/customer/free/**")).permitAll()
                .and()
                .addFilterAfter(jwtAuthorizationFilter, SecurityContextPersistenceFilter.class)
                .authorizeRequests().requestMatchers(new AntPathRequestMatcher("/rest/**")).authenticated()
                .and()
                .addFilterAfter(jwtAuthorizationFilter, SecurityContextPersistenceFilter.class)
                .authorizeRequests().requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilter(loginFilter)
                .addFilterBefore(jwtAuthorizationFilter, LogoutFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
