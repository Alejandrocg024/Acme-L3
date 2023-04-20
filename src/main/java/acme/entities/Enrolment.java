
package acme.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected String			holderName;

	protected Integer			lowerNibble;

	protected boolean			draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Student			student;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;


	public Double workTime(final Collection<Activity> activities) {
		double workTime = 0.0;
		for (final Activity a : activities)
			workTime += MomentHelper.computeDuration(a.getStartPeriod(), a.getEndPeriod()).getSeconds() / 3600.0;
		return workTime;
	}
}
