package ${targetPackage};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ${entityPackage}.${entityClassName};

@Repository
public interface ${entityClassName}Repository extends JpaRepository<${entityClassName},Long> {

}
