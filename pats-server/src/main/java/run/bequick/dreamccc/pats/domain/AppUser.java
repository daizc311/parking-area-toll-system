package run.bequick.dreamccc.pats.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String salt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new HashSet<>();

    private Date createTime;

    private Date updateTime;

}
