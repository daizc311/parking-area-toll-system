package run.bequick.dreamccc.pats.service;


import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser user);

    AppUser getUser(String username);

    List<AppUser> getUsers();

    AppRole saveRole(AppRole role);

    void addRoleToUser(String username, String roleName);
}
