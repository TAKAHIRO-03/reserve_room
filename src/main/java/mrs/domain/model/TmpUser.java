package mrs.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "tmp_usr")
@Data
public class TmpUser {
	@Id
	private String uuid;

	private String userId;

	private String mail;

	private String password;

	private String firstName;

	private String lastName;

	@Enumerated(EnumType.STRING)
	private RoleName roleName;
}
