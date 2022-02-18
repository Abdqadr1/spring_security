package qadr.springsecurity.jwt.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.UserRole;
import qadr.springsecurity.jwt.service.AppUserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserResource {
    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }

    @PostMapping("/role/save")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<UserRole> addRole (@RequestBody UserRole userRole){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(userRole));
    }
    @PostMapping("/role/addtouser")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm/token/{token}")
    public String confirmToken(@PathVariable("token") String token){
        return userService.confirmToken(token);
    }
}
@Data
class RoleToUserForm {
    private String email;
    private String roleName;
}