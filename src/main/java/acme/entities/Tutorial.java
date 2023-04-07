
package acme.entities;

import java.util.Date;
import java.util.List;

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
	protected String			abstract$;

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


	public Double estimatedTotalTime(final List<TutorialSession> sessions) {
		double res = 0.0;
		if (!sessions.isEmpty())
			for (final TutorialSession sesion : sessions) {
				final Date start = sesion.getStartPeriod();
				final Date end = sesion.getEndPeriod();
				double horas = 0.0;
				double porcentajeMinutos = 0.0;
				horas = Math.abs(end.getTime() / 3600000 - start.getTime() / 3600000);
				porcentajeMinutos = Math.abs(end.getTime() / 60000 - start.getTime() / 60000) % 60 * 0.01;
				res += horas + porcentajeMinutos;
				System.out.println(horas + " " + end.getTime() + " " + start.getTime() + "//////" + porcentajeMinutos + "====" + res);
			}
		return res;
	}

}
