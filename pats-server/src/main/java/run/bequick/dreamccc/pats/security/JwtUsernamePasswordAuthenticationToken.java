package run.bequick.dreamccc.pats.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private UserType type;

    public JwtUsernamePasswordAuthenticationToken(UserType type, Object principal, Object credentials) {
        super(principal, credentials);
        this.type = type;
    }

    public JwtUsernamePasswordAuthenticationToken(UserType type, Long userId, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.type = type;
    }

}
