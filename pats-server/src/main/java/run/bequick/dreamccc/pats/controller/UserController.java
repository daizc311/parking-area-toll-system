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
import run.bequick.dreamccc.pats.common.BusinessException;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.param.ChangePasswordParam;
import run.bequick.dreamccc.pats.param.LinkRole2UserParam;
import run.bequick.dreamccc.pats.security.SecurityService;
import run.bequick.dreamccc.pats.service.UserService;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;


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

    @Operation(summary = "获取当前登录用户", tags = {"AppUser", "Login"})
    @PostMapping("/getCurrentLogin")
    public DrResponse<AppUser> getCurrentLogin() {
        var user = securityService.getCurrentAppUser()
                .orElseThrow(() -> new BusinessException("获取当前用户信息失败"));
        return DrResponse.data(user);
    }

    @Operation(summary = "修改当前登录用户密码", tags = {"AppUser"})
    @PostMapping("/changePassword")
    public DrResponse<AppUser> changePassword(@Validated @RequestBody ChangePasswordParam changePasswordParam) {
        var user = securityService.getCurrentAppUser()
                .orElseThrow(() -> new BusinessException("获取当前用户信息失败"));

        user = userService.changePassword(user, changePasswordParam.getOldPassword(), changePasswordParam.getNewPassword());
        return DrResponse.data(user);
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
