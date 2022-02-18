package qadr.springsecurity.jwt.service;

import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.UserRole;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    UserRole saveRole(UserRole role);
    void addRoleToUser(String email, String roleName);
    AppUser getUser(String email);
    List<AppUser> getUsers();

    String confirmToken(String token);
}
