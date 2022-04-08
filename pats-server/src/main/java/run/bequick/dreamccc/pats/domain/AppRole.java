package run.bequick.dreamccc.pats.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "app_role")
@Entity
@NoArgsConstructor
public class AppRole {

    @Id
    @Getter
    private String id;

    @Getter
    @Column(unique = true, nullable = false)
    private String description;

    public AppRole(String id, String description) {
//        super.setId(id);
        this.id = id;
        this.description = description;
    }
}
