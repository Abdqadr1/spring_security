package qadr.springsecurity.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.UserRole;
import qadr.springsecurity.jwt.service.AppUserService;

import java.util.ArrayList;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

	@Bean
	CommandLineRunner run(AppUserService userService) {
		return args -> {
			userService.saveRole(new UserRole(null, "ROLE_USER"));
			userService.saveRole(new UserRole(null, "ROLE_MANAGER"));
			userService.saveRole(new UserRole(null, "ROLE_ADMIN"));
			userService.saveRole(new UserRole(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser("John Travolta", "john@gmail.com", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser("Will Smith", "will@icloud.com", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser("Jim Carry", "jim@yahoo.com", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser("Arnold Schwarzenegger", "arnold@gmail.com", "1234", new ArrayList<>()));

			userService.addRoleToUser("john@gmail.com", "ROLE_USER");
			userService.addRoleToUser("will@icloud.com", "ROLE_MANAGER");
			userService.addRoleToUser("jim@yahoo.com", "ROLE_ADMIN");
			userService.addRoleToUser("arnold@gmail.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("arnold@gmail.com", "ROLE_USER");
		};
	}


}
