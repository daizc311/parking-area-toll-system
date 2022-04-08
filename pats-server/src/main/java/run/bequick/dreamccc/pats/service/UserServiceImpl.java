package run.bequick.dreamccc.pats.service;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.bequick.dreamccc.pats.common.ServiceLog;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.enums.AppRoleEnum;
import run.bequick.dreamccc.pats.repository.AppRoleRepository;
import run.bequick.dreamccc.pats.repository.AppUserRepository;
import run.bequick.dreamccc.pats.security.JwtUserDetail;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
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
            String format = StrFormatter.format("用户{}不存在", username);
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
    @ServiceLog("初始化用户与权限 - {pos}")
    public void init() {

        boolean allExists = appRoleRepository.existsAllByIdIn(Arrays.stream(AppRoleEnum.values()).map(Objects::toString).collect(Collectors.toList()));
        if (!allExists) {
            appRoleRepository.deleteAll();
            Arrays.stream(AppRoleEnum.values())
                    .map(appRoleEnum -> new AppRole(appRoleEnum.toString(), appRoleEnum.getDescription()))
//                    .peek(role -> role.setNew())
                    .forEach(appRoleRepository::save);
        }

        AppUser admin = appUserRepository.findByUsername("admin");
        if (admin == null) {
            String adminPwd = UUID.fastUUID().toString(true);
            admin = new AppUser();
            admin.setUsername("admin");
            admin.setRoles(appRoleRepository.findAll());
            admin.setName("超级管理员");
            admin.setPassword(passwordEncoder.encode(adminPwd));
            admin = appUserRepository.save(admin);
            log.warn("admin密码已生成:{}", adminPwd);
        }
    }

}
