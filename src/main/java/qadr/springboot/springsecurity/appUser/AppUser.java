package qadr.springboot.springsecurity.appUser;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name="users")
public class AppUser implements UserDetails {
	
	@Id
	@SequenceGenerator(
			name="user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="user_sequence")
	private Long id;
	private String fullname;
	private String email;
	private String password;
	private Boolean enabled = false;
	private Boolean locked = false;
	@Enumerated(EnumType.STRING)
	private AppUserRole appUserRole;
	
	public AppUser() {}

	public AppUser(String fullname, String email, String password,AppUserRole appUserRole) {
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.appUserRole = appUserRole;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(appUserRole.name());
		return Collections.singletonList(simpleAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setPassword(String encode) {
		// TODO Auto-generated method stub
		password = encode;
	}

}
