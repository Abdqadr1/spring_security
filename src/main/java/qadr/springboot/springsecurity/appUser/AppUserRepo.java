package qadr.springboot.springsecurity.appUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long>{
	
	Optional<AppUser> findByEmail(String email);

}
