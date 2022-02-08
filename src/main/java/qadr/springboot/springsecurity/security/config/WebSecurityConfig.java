package qadr.springboot.springsecurity.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import qadr.springboot.springsecurity.appUser.AppUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private AppUserService appUserService;
	
	
	@Autowired
	public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, AppUserService appUserService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.appUserService = appUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "/home", "/api/v*/registration/**")
		.permitAll()
		.anyRequest().authenticated() // any other request must be authenticated
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider());
	}
	
	@Bean
	public DaoAuthenticationProvider provider() {
		DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(appUserService);
		return provider;
	}
	
	
}
