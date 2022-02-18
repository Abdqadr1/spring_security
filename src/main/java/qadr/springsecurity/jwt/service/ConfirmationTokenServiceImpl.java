package qadr.springsecurity.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import qadr.springsecurity.jwt.exception.ApiRequestException;
import qadr.springsecurity.jwt.model.AppUser;
import qadr.springsecurity.jwt.model.ConfirmationToken;
import qadr.springsecurity.jwt.repo.ConfirmationTokenRepo;

@Service @RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{
    private final ConfirmationTokenRepo tokenRepo;
    @Override
    public ConfirmationToken saveToken(ConfirmationToken confirmationToken) {
        return tokenRepo.save(confirmationToken);
    }

    @Override
    public ConfirmationToken findByToken(String token) {
        return tokenRepo.findByToken(token)
                .orElseThrow(()->
                        new ApiRequestException("No confirmation token found!", HttpStatus.BAD_REQUEST)
                );
    }

    @Override
    public ConfirmationToken findByAppUser(AppUser appUser) {
        return tokenRepo.findByAppUser(appUser)
                .orElseThrow(()->
                        new ApiRequestException("No confirmation token found!", HttpStatus.BAD_REQUEST)
                );
    }
}
