
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 75)
	protected String			author;

	@NotBlank
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			furtherInformation;


	@Transient
	protected void parseAuthor(final String username, final String name, final String surname) {
		final String res = username + " - " + surname + ", " + name;
		this.author = res;
	}

}
