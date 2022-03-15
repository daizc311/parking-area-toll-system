package run.bequick.dreamccc.pats.business.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import run.bequick.dreamccc.pats.common.DrResponse;
import run.bequick.dreamccc.pats.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public DrResponse<User> getUserById(@PathVariable Long id){

        User user = userRepository.findById(id).orElse(null);
        return DrResponse.data(user);
    }

    @PostMapping("/{id}")
    public DrResponse<User> editUser(@PathVariable Long id,@RequestBody User editUser){

        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(user -> {
            editUser.setId(null);
            BeanUtils.copyProperties(editUser,user);
            userRepository.save(user);
        });

        return DrResponse.data(userRepository.findById(id).orElse(null));
    }

}
