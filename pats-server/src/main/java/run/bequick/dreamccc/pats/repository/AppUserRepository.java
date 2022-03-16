package run.bequick.dreamccc.pats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.domain.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUsername(String username);


}
