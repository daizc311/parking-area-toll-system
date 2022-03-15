package run.bequick.dreamccc.pats.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser,Long> {

    AppUser findByUsername(String username);
}
