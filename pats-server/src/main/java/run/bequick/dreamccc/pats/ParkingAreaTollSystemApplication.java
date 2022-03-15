package run.bequick.dreamccc.pats;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.service.UserService;

import java.util.ArrayList;
import java.util.Date;

// https://www.youtube.com/watch?v=VVn9OG9nfH0
@SpringBootApplication
public class ParkingAreaTollSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingAreaTollSystemApplication.class, args);
    }


    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner runner1(UserService userService) {
//        return args -> {
//            userService.saveRole(new AppRole(null, "ROLE_USER"));
//            userService.saveRole(new AppRole(null, "ROLE_MANAGER"));
//            userService.saveRole(new AppRole(null, "ROLE_ADMIN"));
//            userService.saveRole(new AppRole(null, "ROLE_SUPER_ADMIN"));
//
//            userService.saveUser(new AppUser(null, "Alice", "alice", "dzc9669", "", new ArrayList<>(), new Date(), new Date()));
//            userService.saveUser(new AppUser(null, "Bob", "bob", "dzc9669", "", new ArrayList<>(), new Date(), new Date()));
//            userService.saveUser(new AppUser(null, "Cai", "cai", "dzc9669", "", new ArrayList<>(), new Date(), new Date()));
//            userService.saveUser(new AppUser(null, "Daizc", "daizc", "dzc9669", "", new ArrayList<>(), new Date(), new Date()));
//
//            userService.linkRole2UserWithName("ROLE_SUPER_ADMIN","Daizc");
//            userService.linkRole2UserWithName("ROLE_ADMIN","Cai");
//            userService.linkRole2UserWithName("ROLE_MANAGER","Bob");
//            userService.linkRole2UserWithName("ROLE_USER","Alice");
//        };
//    }
}
