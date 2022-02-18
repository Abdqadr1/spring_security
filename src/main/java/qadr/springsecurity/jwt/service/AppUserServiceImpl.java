package qadr.springsecurity.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qadr.springsecurity.jwt.email.EmailService;
import qadr.springsecurity.jwt.exception.ApiRequestException;
import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.ConfirmationToken;
import qadr.springsecurity.jwt.model.UserRole;
import qadr.springsecurity.jwt.repo.AppUserRepo;
import qadr.springsecurity.jwt.repo.ConfirmationTokenRepo;
import qadr.springsecurity.jwt.repo.UserRoleRepo;

import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
@Transactional @Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepo userRepo;
    private final UserRoleRepo roleRepo;
    private final ConfirmationTokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public AppUser saveUser(AppUser user) {
        log.error("saving user to db "+ user.getPassword());
        Optional<AppUser> appUser = userRepo.findByEmail(user.getEmail());
        if(appUser.isPresent()){
            throw new ApiRequestException("Email already exists", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String token = UUID.randomUUID().toString();
        tokenService.saveToken(new ConfirmationToken(
               token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        ));
        String link = "http://localhost:8080/api/confirm/token/"+token;
        emailService.send(user.getEmail(), emailService.buildEmail(user.getName(), link));
        return userRepo.save(user);
    }

    @Override
    public UserRole saveRole(UserRole role) {
        log.info("saving role to db");
        Optional<UserRole> userRole = roleRepo.findByName(role.getName());
        if(userRole.isPresent()){
            throw new ApiRequestException("Role already exists", HttpStatus.BAD_REQUEST);
        }
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("adding role {} to user {}", roleName, email);
        AppUser user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                    new ApiRequestException(
                            String.format("User with email %s not found", email),
                            HttpStatus.BAD_REQUEST));
        log.error("User details {}", user.getPassword());
        UserRole role = roleRepo.findByName(roleName.toUpperCase())
                .orElseThrow(()->
                        new ApiRequestException(
                                String.format("Role name %S not found", roleName),
                                HttpStatus.BAD_REQUEST));
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String email) {
        log.info("fetching user by email");
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(
                        String.format("User with email %s not found", email),
                        HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("fetching all app users");
        return userRepo.findAll();
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken  = tokenService.findByToken(token);
        AppUser user = confirmationToken.getAppUser();
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ApiRequestException("Email already confirmed", HttpStatus.BAD_REQUEST);
        }
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new ApiRequestException("Token has expired", HttpStatus.BAD_REQUEST);
        }
        user.setEnabled(true);
        return "Email Confirmed";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(email)
                .orElseThrow(()-> new ApiRequestException(
                        String.format("User with email %s not found", email),
                        HttpStatus.BAD_REQUEST));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new User(
                user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }
}
