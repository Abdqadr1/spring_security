package qadr.springboot.springsecurity.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import qadr.springboot.springsecurity.appUser.AppUser;
import qadr.springboot.springsecurity.appUser.AppUserRole;
import qadr.springboot.springsecurity.appUser.AppUserService;

@Service
@AllArgsConstructor
public class RegistrationService {
	private EmailValidator emailValidator;
	private AppUserService appUserService;
	
	@Autowired
	public RegistrationService(EmailValidator emailValidator, AppUserService appUserService) {
		this.emailValidator = emailValidator;
		this.appUserService = appUserService;
	}



	public String register(RegistrationRequest request) {
		boolean isEmailValid = emailValidator.test(request.getEmail());
		
		if (!isEmailValid) {
			throw new IllegalStateException("Enter a valid email address");
		}
		return appUserService.signUpUser(
				new AppUser(
						request.getFullname(),
						request.getEmail(),
						request.getPassword(),
						AppUserRole.USER)
				);
	}
}
