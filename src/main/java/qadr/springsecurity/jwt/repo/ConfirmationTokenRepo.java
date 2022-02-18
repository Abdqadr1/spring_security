package qadr.springsecurity.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByAppUser(AppUser user);
    Optional<ConfirmationToken> findByToken(String token);
}
