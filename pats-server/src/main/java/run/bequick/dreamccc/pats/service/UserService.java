package run.bequick.dreamccc.pats.service;


import run.bequick.dreamccc.pats.domain.AppUser;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser user);

    AppUser getUser(String username);

    List<AppUser> getUsers();

    void linkRole2User(String roleId, Long userId);

    void linkRole2UserWithName(String roleName, String username);

    void init();
}
