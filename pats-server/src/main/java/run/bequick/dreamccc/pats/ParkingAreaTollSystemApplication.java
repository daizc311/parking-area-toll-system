package run.bequick.dreamccc.pats;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import run.bequick.dreamccc.pats.domain.AppRole;
import run.bequick.dreamccc.pats.domain.AppUser;
import run.bequick.dreamccc.pats.service.UserService;
import run.bequick.dreamccc.pats.service.data.ParkingSettingDService;

import java.util.ArrayList;
import java.util.Date;

@Slf4j
@OpenAPIDefinition(security = {@SecurityRequirement(name = "token")})
@SpringBootApplication
public class ParkingAreaTollSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingAreaTollSystemApplication.class, args);
    }


    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner1(ParkingSettingDService parkingSettingDService) {
        return arg -> {
            parkingSettingDService.init();
        };
    }

//    @Bean
//    CommandLineRunner runner1(UserService userService) {
//        return args -> {
//            userService.saveRole(new AppRole("ROLE_USER"));
//            userService.saveRole(new AppRole("ROLE_MANAGER"));
//            userService.saveRole(new AppRole("ROLE_ADMIN"));
//            userService.saveRole(new AppRole("ROLE_SUPER_ADMIN"));
//
//            userService.saveUser(new AppUser( "Alice", "alice", "dzc9669", "", new ArrayList<>()));
//            userService.saveUser(new AppUser("Bob", "bob", "dzc9669", "", new ArrayList<>()));
//            userService.saveUser(new AppUser("Cai", "cai", "dzc9669", "", new ArrayList<>()));
//            userService.saveUser(new AppUser("Daizc", "daizc", "dzc9669", "", new ArrayList<>()));
//
//            userService.linkRole2UserWithName("ROLE_SUPER_ADMIN","Daizc");
//            userService.linkRole2UserWithName("ROLE_ADMIN","Cai");
//            userService.linkRole2UserWithName("ROLE_MANAGER","Bob");
//            userService.linkRole2UserWithName("ROLE_USER","Alice");
//        };
//    }
}
