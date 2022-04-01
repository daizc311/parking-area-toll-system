package ${dServiceImplPackage};

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ${repoPackage}.${repoClassName};

@Service
@RequiredArgsConstructor
public class ${dServiceImplClassName} implements ${dServiceClassName}{
    private final ${repoClassName} repository;
}
