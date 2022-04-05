package run.bequick.dreamccc.pats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.param.LinkRole2UserParam;
import run.bequick.dreamccc.pats.service.UserService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/user")
    public ResponseEntity<List<AppUser>> allUsers(){

        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping("/user")
    public ResponseEntity<AppUser> saveUser(@Validated @RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PutMapping("/role")
    public ResponseEntity<AppRole> saveRole(@Validated @RequestBody AppRole role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/link")
    public ResponseEntity<?> linkRole2User(@Validated @RequestBody LinkRole2UserParam role2UserForm){
        userService.linkRole2User(role2UserForm.getRoleId(), role2UserForm.getUserId());
        return ResponseEntity.ok().build();
    }


//
//    @GetMapping("/{id}")
//    public DrResponse<AppUser> getUserById(@PathVariable Long id){
//
//        AppUser appUser = userService.findById(id).orElse(null);
//        return DrResponse.data(appUser);
//    }
//
//    @PostMapping("/{id}")
//    public DrResponse<AppUser> editUser(@PathVariable Long id, @RequestBody AppUser editAppUser){
//
//        Optional<AppUser> optionalUser = userService.findById(id);
//        optionalUser.ifPresent(appUser -> {
//            editAppUser.setId(null);
//            BeanUtils.copyProperties(editAppUser, appUser);
//            userService.saveUser(appUser);
//        });
//
//        return DrResponse.data(userService.findById(id).orElse(null));
//    }

}
