package run.bequick.dreamccc.pats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.bequick.dreamccc.pats.common.ColumnComment;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Table(name = "app_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AppUser extends AbstractAuditable<AppUser, Long> {


    @Column(nullable = false)
    @ColumnComment("显示名称")
    private String name;

    @Column(unique = true, nullable = false)
    @ColumnComment("登录名称")
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    @ColumnComment("登录密码")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new HashSet<>();

}
