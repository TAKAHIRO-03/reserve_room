package mrs.app.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import mrs.domain.model.RoleName;

@Data
public class SignupForm {

	@NotBlank
	@Length(min = 4, max = 8)
	@Pattern(regexp="^[a-zA-Z0-9]+$")
	private String userId;

	@NotBlank
	@Length(min = 1, max = 8)
	private String firstName;

	@NotBlank
	@Length(min = 1, max = 8)
	private String lastName;

	@NotBlank
	@Email
	private String mail;

	@NotBlank
	private String password;

	private RoleName roleName;

}
