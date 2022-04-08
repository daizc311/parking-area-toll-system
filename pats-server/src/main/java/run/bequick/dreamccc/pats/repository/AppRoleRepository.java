package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.AppRole;

import java.util.Collection;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, String> {

    boolean existsAllByIdIn(Collection<String> name);
}
