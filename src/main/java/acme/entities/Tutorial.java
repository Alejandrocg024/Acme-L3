
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tutorial extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotBlank
	@Length(max = 100)
	protected String			goal;

	protected boolean			draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Assistant			assistant;

}
