package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

    AppRole findByName(String name);
}
