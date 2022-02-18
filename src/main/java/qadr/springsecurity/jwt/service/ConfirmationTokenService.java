package qadr.springsecurity.jwt.service;

import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken saveToken(ConfirmationToken confirmationToken);
    ConfirmationToken findByToken (String token);
    ConfirmationToken findByAppUser(AppUser appUser);
}
