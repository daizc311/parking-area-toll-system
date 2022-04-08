package run.bequick.dreamccc.pats.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bequick.dreamccc.pats.param.LinkRole2UserParam;
import run.bequick.dreamccc.pats.service.UserService;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


//    @RequestMapping(value = "/listAll",method = {RequestMethod.GET,RequestMethod.POST})
//    public DrResponse<List<AppUser>> listAll(){
//
//        return DrResponse.data(userService.getUsers());
//    }
//
//    @RequestMapping(value = "/save",method = {RequestMethod.PUT,RequestMethod.POST})
//    public DrResponse<AppUser> save(@Validated @RequestBody AppUser user){
//        return DrResponse.data(userService.saveUser(user));
//    }
//
//    @PutMapping("/role")
//    public ResponseEntity<AppRole> saveRole(@Validated @RequestBody AppRole role){
//        return DrResponse.data(userService.saveRole(role));
//    }

    @Operation(summary = "用户关联权限", tags = {"AppUser", "AppRole"})
    @PostMapping("/link/role")
    public ResponseEntity<?> userLinkRole(@Validated @RequestBody LinkRole2UserParam role2UserForm) {
        userService.linkRole2User(role2UserForm.getRole(), role2UserForm.getUserId());
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
