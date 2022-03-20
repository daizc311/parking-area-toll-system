package run.bequick.dreamccc.pats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Data
@Table(name = "app_user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new HashSet<>();

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}
