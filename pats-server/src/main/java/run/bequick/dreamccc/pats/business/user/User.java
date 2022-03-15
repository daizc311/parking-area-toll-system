package run.bequick.dreamccc.pats.business.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    private Long id;

    private String name;

    private String email;

    private String password;

    private String salt;

    private Date createTime;

    private Date updateTime;

}
