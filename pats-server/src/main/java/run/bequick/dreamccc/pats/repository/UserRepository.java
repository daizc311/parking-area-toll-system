package run.bequick.dreamccc.pats.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import run.bequick.dreamccc.pats.business.user.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
