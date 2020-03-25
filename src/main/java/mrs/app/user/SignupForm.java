package mrs.app.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import mrs.domain.model.RoleName;

@Data
public class SignupForm {

	@NotBlank
	private String userId;

	@NotBlank
	@Email
	private String mail;

	@NotBlank
	private String password;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private RoleName roleName;

}
