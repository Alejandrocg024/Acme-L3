
package acme.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.datatypes.Mark;
import acme.framework.data.AbstractEntity;

public class Audit extends AbstractEntity {

	protected static final long		serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String				code;

	@NotBlank
	@Length(max = 100)
	protected String				conclusion;

	@NotBlank
	@Length(max = 100)
	protected String				strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String				weakPoints;

	@OneToMany()
	protected List<AuditingRecords>	auditingRecords;


	public Mark getMark() {
		return null;
	};
}
