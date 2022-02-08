package qadr.springboot.springsecurity.registration;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationRequest {

	private String fullname;
	private String email;
	private String password;
	
	public RegistrationRequest(String fullname, String email, String password) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, fullname, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRequest other = (RegistrationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(fullname, other.fullname)
				&& Objects.equals(password, other.password);
	}
	

}
