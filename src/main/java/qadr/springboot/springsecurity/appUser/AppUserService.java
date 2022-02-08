package qadr.springboot.springsecurity.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
	
	private final AppUserRepo appUserRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Autowired
	public AppUserService(AppUserRepo appUserRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepo = appUserRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDetails user =  appUserRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
		System.out.println(user.toString());
		return user;
	}


	public String signUpUser(AppUser appUser) {
		// TODO Auto-generated method stub
		boolean exists = appUserRepo.findByEmail(appUser.getUsername()).isPresent();
		if(exists) {
			throw new IllegalStateException("Email already exists");
		}
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUserRepo.save(appUser);
		//TODO send confirmation token and email
		return "Registered Successfully";
	}

}
