package qadr.springsecurity.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import qadr.springsecurity.jwt.model.AppUser;

import java.util.Optional;

/**
 * @author Get Arrays (https://www.getarrays.io/)
 * @version 1.0
 * @since 7/10/2021
 */
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
