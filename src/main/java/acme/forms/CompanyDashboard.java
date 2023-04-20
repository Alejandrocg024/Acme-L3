
package acme.forms;

import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected int[]				numberOfPracticaPerMonth;
	protected Statistic			periodLengthOfSessionsStats;
	protected Statistic			periodLengthOfPracticaStats;

}
