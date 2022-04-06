package run.bequick.dreamccc.pats.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.domain.Customer;
import run.bequick.dreamccc.pats.repository.AppUserRepository;
import run.bequick.dreamccc.pats.repository.CustomerRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final CustomerRepository customerRepository;
    private final AppUserRepository appUserRepository;

    /**
     * 从SecurityContextHolder中获取AdminJwtAuthentication
     *
     * @return 可能有也可能没有的AdminJwtAuthentication
     */
    public Optional<JwtUsernamePasswordAuthenticationToken> getAuthentication() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication instanceof JwtUsernamePasswordAuthenticationToken)
                .map(authentication -> (JwtUsernamePasswordAuthenticationToken) authentication);
    }

    /**
     * 获取当前登陆客户
     *
     * @return 可能有也可能没有的Customer
     */
    public Optional<Customer> getCurrentCustomer() {

        return getAuthentication().filter(token -> Objects.equals(token.getType(), UserType.CUSTOMER))
                .map(JwtUsernamePasswordAuthenticationToken::getUserId)
                .map(customerRepository::getById);
    }

    /**
     * 获取当前登陆系统用户
     *
     * @return 可能有也可能没有的AppUser
     */
    public Optional<AppUser> getCurrentAppUser() {

        return getAuthentication().filter(token -> Objects.equals(token.getType(), UserType.APP_USER))
                .map(JwtUsernamePasswordAuthenticationToken::getUserId)
                .map(appUserRepository::getById);
    }

}
