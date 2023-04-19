
package acme.forms;

import java.util.Collection;
import java.util.Date;

import acme.datatypes.Statistic;
import acme.entities.PracticumSession;
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


	public int[] countMonthsPerPracticum(final Collection<PracticumSession> practicumSessions) {
		final int[] monthsPerPracticum = new int[12];
		for (final PracticumSession ps : practicumSessions) {
			final Date startPeriod = ps.getStartPeriod();
			final Date endPeriod = ps.getEndPeriod();
			int startMonth = startPeriod.getMonth();
			final int endMonth = endPeriod.getMonth();
			while (startMonth <= endMonth) {
				monthsPerPracticum[startMonth] += 1;
				startMonth += 1;
			}
		}
		for (int i = 0; i < 12; i++)
			if (monthsPerPracticum[i] > 0)
				monthsPerPracticum[i] = 1;
			else
				monthsPerPracticum[i] = 0;
		return monthsPerPracticum;
	}

}
