package ${repoPackage};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ${entityPackage}.${entityClassName};

@Repository
public interface ${repoClassName} extends JpaRepository<${entityClassName},Long> {

}
