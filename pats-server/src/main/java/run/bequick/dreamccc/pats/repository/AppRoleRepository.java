package run.bequick.dreamccc.pats.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;

@Repository
public interface AppRoleRepository extends CrudRepository<AppRole,Long> {

    AppRole findByName(String name);
}
