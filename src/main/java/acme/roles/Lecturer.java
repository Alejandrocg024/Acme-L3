
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecturer extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	protected String			almaMater;

	@NotBlank
	@Length(max = 101)
	protected String			resume;

	@NotBlank
	@Length(max = 101)
	protected String			listOfQualifications;

	@URL
	protected String			furtherInformation;
}
