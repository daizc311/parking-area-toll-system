package run.bequick.dreamccc.pats.service;


import cn.hutool.core.text.StrFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.enums.AppRoleEnum;
import run.bequick.dreamccc.pats.repository.AppRoleRepository;
import run.bequick.dreamccc.pats.repository.AppUserRepository;
import run.bequick.dreamccc.pats.security.JwtUserDetail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("saveUser: {}", user.getName());
        String encodePwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);
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

//    @Override
//    public AppRole saveRole(AppRole role) {
//        log.info("saveRole: {}", role.getName());
//        return appRoleRepository.save(role);
//    }

    @Override
    public void linkRole2User(String roleId, Long userId) {
        AppUser user = appUserRepository.findById(userId).orElse(null);
        AppRole role = appRoleRepository.findById(roleId).orElse(null);
        if (Objects.nonNull(user) && Objects.nonNull(role)) {
            log.info("bindRole2User: {},{}", user.getName(), role.getId());
            user.getRoles().add(role);
            appUserRepository.save(user);
        }
    }

    @Override
    public void linkRole2UserWithName(String roleName, String username) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role = appRoleRepository.findById(roleName).orElse(null);
        if (Objects.nonNull(user) && Objects.nonNull(role)) {
            log.info("bindRole2User: {},{}", user.getName(), role.getId());
            user.getRoles().add(role);
            appUserRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByUsername(username);
        if (Objects.isNull(appUser)) {
            String format = StrFormatter.format("??????{}?????????", username);
            log.info(format);
            throw new UsernameNotFoundException(format);
        }
        var authorities = appUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getId()))
                .collect(Collectors.toList());

        return new JwtUserDetail(appUser.getId(), username, appUser.getPassword(), authorities);
    }

    @Override
    @ServiceLog("???????????????????????? - {pos}")
    public void init() {

        boolean allExists = appRoleRepository.existsAllByIdIn(Arrays.stream(AppRoleEnum.values()).map(Objects::toString).collect(Collectors.toList()));
        if (!allExists) {
            appRoleRepository.deleteAll();
            Arrays.stream(AppRoleEnum.values())
                    .map(appRoleEnum -> new AppRole(appRoleEnum.toString(), appRoleEnum.getDescription()))
                    .forEach(appRoleRepository::save);
        }

        AppUser admin = appUserRepository.findByUsername("admin");
        if (admin == null) {
            String adminPwd = "admin";
            admin = new AppUser();
            admin.setUsername("admin");
            admin.setRoles(appRoleRepository.findAll());
            admin.setName("???????????????");
            admin.setPassword(passwordEncoder.encode(adminPwd));
            admin = appUserRepository.save(admin);
            log.warn("admin???????????????:{}", adminPwd);
        }
        AppUser garageManager = appUserRepository.findByUsername("garageManager");
        if (garageManager == null) {
            String garageManagerPwd = "garageManager";
            garageManager = new AppUser();
            garageManager.setUsername("garageManager");
            final var appRole = appRoleRepository.findById(AppRoleEnum.ROLE_GARAGE_MANAGER)
                    .orElse(new AppRole(
                            AppRoleEnum.GARAGE_MANAGER.toString(),
                            AppRoleEnum.GARAGE_MANAGER.getDescription()
                    ));
            garageManager.setRoles(Collections.singletonList(appRole));
            garageManager.setName("???????????????");
            garageManager.setPassword(passwordEncoder.encode(garageManagerPwd));
            garageManager = appUserRepository.save(garageManager);
            log.warn("garageManager???????????????:{}", garageManagerPwd);
        }
        AppUser financialManager = appUserRepository.findByUsername("financialManager");
        if (financialManager == null) {
            String financialManagerPwd = "financialManager";
            financialManager = new AppUser();
            financialManager.setUsername("financialManager");
            final var appRole = appRoleRepository.findById(AppRoleEnum.ROLE_FINANCIAL_MANAGER)
                    .orElse(new AppRole(
                            AppRoleEnum.FINANCIAL_MANAGER.toString(),
                            AppRoleEnum.FINANCIAL_MANAGER.getDescription()
                    ));
            financialManager.setRoles(Collections.singletonList(appRole));
            financialManager.setName("????????????");
            financialManager.setPassword(passwordEncoder.encode(financialManagerPwd));
            financialManager = appUserRepository.save(financialManager);
            log.warn("financialManager???????????????:{}", financialManagerPwd);
        }

    }

    @Override
    public AppUser changePassword(AppUser user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        return appUserRepository.save(user);
    }

    @Override
    public AppUser changePassword(AppUser user, String oldPassword, String newPassword) {
        final var pwdMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!pwdMatch) {
            throw new BusinessException("??????????????????");
        }
        return changePassword(user, newPassword);
    }

}
