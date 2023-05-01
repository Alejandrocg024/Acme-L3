
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
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$", message = "{validation.enrolment.reference}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company			company;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;


	public String estimatedTotalTime(final Collection<PracticumSession> practicumSessions) {
		double exactHours = 0.0;
		String estimatedTotalTime;
		for (final PracticumSession ps : practicumSessions)
			exactHours += MomentHelper.computeDuration(ps.getStartPeriod(), ps.getEndPeriod()).getSeconds() / 3600.0;
		estimatedTotalTime = String.format("%.2f horas ± %.2f horas", exactHours, exactHours * 0.1);
		return estimatedTotalTime;
	}

}
