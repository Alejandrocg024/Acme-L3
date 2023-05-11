
package acme.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------

	@NotBlank
	public String				holderName;

	@NotBlank
	public String				creditCardNumber;

	@NotBlank
	@Pattern(regexp = "^\\d{2}\\/\\d{2}$", message = "{validation.payment.expirationDate}")
	public String				expirationDate;

	@NotBlank
	@Pattern(regexp = "^\\d{3}$", message = "{validation.payment.securityCode}")
	public String				securityCode;

	// Response attributes ----------------------------------------------------

}
