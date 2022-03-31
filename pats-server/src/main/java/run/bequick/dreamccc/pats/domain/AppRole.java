package run.bequick.dreamccc.pats.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "app_role")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppRole extends AbstractAuditable<AppUser, Long> {

    @Column(unique = true, nullable = false)
    private String name;
}
