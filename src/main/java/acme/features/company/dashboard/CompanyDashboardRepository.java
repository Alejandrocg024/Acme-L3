
package acme.features.company.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.id = :companyId")
	Company findCompanyById(int companyId);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findPracticaByCompanyId(int companyId);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :practicumId")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int practicumId);

	@Query("select ps from PracticumSession ps where ps.practicum.company.id = :companyId")
	Collection<PracticumSession> findPracticumSessionsByCompanyId(int companyId);

	default int[] countMonthsPerPracticum(final Collection<PracticumSession> practicumSessions) {
		final int[] monthsPerPracticum = new int[12];
		for (final PracticumSession ps : practicumSessions)
			if (MomentHelper.isAfterOrEqual(ps.getStartPeriod(), MomentHelper.deltaFromMoment(MomentHelper.getCurrentMoment(), -1, ChronoUnit.YEARS)) && MomentHelper.isBeforeOrEqual(ps.getStartPeriod(), MomentHelper.getCurrentMoment())) {
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

	default int[] getNumberOfPracticaPerMonth(final int companyId) {
		final int[] numberOfPracticaPerMonth = new int[12];
		for (final Practicum p : this.findPracticaByCompanyId(companyId)) {
			int[] monthsPerPracticum;
			monthsPerPracticum = this.countMonthsPerPracticum(this.findPracticumSessionsByPracticumId(p.getId()));
			for (int i = 0; i <= 11; i++)
				numberOfPracticaPerMonth[i] += monthsPerPracticum[i];
		}
		return numberOfPracticaPerMonth;
	}

}
