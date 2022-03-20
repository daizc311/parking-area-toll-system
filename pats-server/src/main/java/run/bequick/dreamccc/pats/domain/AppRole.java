package run.bequick.dreamccc.pats.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "app_role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
