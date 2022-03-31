package run.bequick.dreamccc.pats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Table(name = "app_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends AbstractAuditable<AppUser, Long> implements LoginAble {


    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private String salt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new HashSet<>();

}
