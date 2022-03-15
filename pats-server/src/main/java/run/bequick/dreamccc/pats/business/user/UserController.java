package run.bequick.dreamccc.pats.business.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.repository.AppUserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserRepository appUserRepository;

    @GetMapping("/{id}")
    public DrResponse<AppUser> getUserById(@PathVariable Long id){

        AppUser appUser = appUserRepository.findById(id).orElse(null);
        return DrResponse.data(appUser);
    }

    @PostMapping("/{id}")
    public DrResponse<AppUser> editUser(@PathVariable Long id, @RequestBody AppUser editAppUser){

        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        optionalUser.ifPresent(appUser -> {
            editAppUser.setId(null);
            BeanUtils.copyProperties(editAppUser, appUser);
            appUserRepository.save(appUser);
        });

        return DrResponse.data(appUserRepository.findById(id).orElse(null));
    }

}
