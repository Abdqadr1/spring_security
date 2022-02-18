package qadr.springsecurity.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import qadr.springsecurity.jwt.model.UserRole;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}
