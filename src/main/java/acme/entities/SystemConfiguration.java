
package acme.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^[A-Z]{3}$", message = "{validation.systemConfiguration.systemCurrency}")
	@NotNull
	protected String			systemCurrency;

	@Pattern(regexp = "^([A-Z]{3},)*[A-Z]{3}$", message = "{validation.systemConfiguration.aceptedCurrencies}")
	@NotNull
	protected String			aceptedCurrencies;

	@NotBlank
	@Pattern(regexp = "^(\"[^\"]*\"|[^\",]+)(,(\"[^\"]*\"|[^\",]+))*$", message = "{validation.systemConfiguration.spamWords}")
	protected String			spamWords;

	@Range(min = 0, max = 1)
	protected double			spamThreshold;

}
