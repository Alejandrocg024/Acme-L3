
package acme.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
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
	protected String			goal;

	protected boolean			draftMode;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Course			course;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Assistant			assistant;


	@Transient
	public Double estimatedTotalTime(final Collection<TutorialSession> collection) {
		double res = 0.0;
		if (!collection.isEmpty())
			for (final TutorialSession sesion : collection) {
				final Date start = sesion.getStartPeriod();
				final Date end = sesion.getEndPeriod();
				double horas = 0.0;
				double minutos = 0.0;
				horas = Math.abs(end.getTime() / 3600000 - start.getTime() / 3600000);
				minutos = Math.abs(end.getTime() / 60000 - start.getTime() / 60000) % 60;
				final double porcentajeMinutos = minutos / 60;
				res += horas + porcentajeMinutos;
			}
		return res;
	}

}
