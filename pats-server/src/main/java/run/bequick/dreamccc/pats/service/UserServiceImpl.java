package run.bequick.dreamccc.pats.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.repository.AppRoleRepository;
import run.bequick.dreamccc.pats.repository.AppUserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("saveUser: {}", user.getName());
        return appUserRepository.save(user);
    }

    @Override
    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppRole saveRole(AppRole role) {
        log.info("saveRole: {}", role.getName());
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role = appRoleRepository.findByName(roleName);
        if (Objects.nonNull(user) && Objects.nonNull(role)) {
            log.info("bindRole2User: {},{}", roleName, username);
            user.getRoles().add(role);
            appUserRepository.save(user);
        }
    }
}
