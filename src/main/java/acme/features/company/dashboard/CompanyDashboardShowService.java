
package acme.features.company.dashboard;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.forms.CompanyDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	@Autowired
	protected CompanyDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CompanyDashboard dashboard;
		int[] numberOfPracticaPerMonth;
		Statistic periodLengthOfSessionsStats;
		Statistic periodLengthOfPracticaStats;

		final Collection<Double> durationOfPractica;
		final Collection<Double> durationOfPracticumSessions;

		Principal principal;
		int companyId;

		principal = super.getRequest().getPrincipal();
		companyId = principal.getActiveRoleId();

		numberOfPracticaPerMonth = this.repository.getNumberOfPracticaPerMonth(companyId);

		durationOfPractica = new ArrayList<Double>();
		for (final Practicum p : this.repository.findPracticaByCompanyId(companyId)) {
			final Collection<PracticumSession> practicumSessions = this.repository.findPracticumSessionsByPracticumId(p.getId());
			Double exactHours = 0.0;
			if (practicumSessions != null)
				for (final PracticumSession ps : practicumSessions)
					exactHours += MomentHelper.computeDuration(ps.getStartPeriod(), ps.getEndPeriod()).getSeconds() / 3600.0;
			durationOfPractica.add(exactHours);
		}

		periodLengthOfPracticaStats = new Statistic();
		periodLengthOfPracticaStats.calcAverage(durationOfPractica);
		periodLengthOfPracticaStats.calcDev(durationOfPractica);
		periodLengthOfPracticaStats.calcMin(durationOfPractica);
		periodLengthOfPracticaStats.calcMax(durationOfPractica);

		durationOfPracticumSessions = new ArrayList<Double>();
		for (final PracticumSession ps : this.repository.findPracticumSessionsByCompanyId(companyId)) {
			final Double duration = MomentHelper.computeDuration(ps.getStartPeriod(), ps.getEndPeriod()).getSeconds() / 3600.0;
			durationOfPracticumSessions.add(duration);
		}

		periodLengthOfSessionsStats = new Statistic();
		periodLengthOfSessionsStats.calcAverage(durationOfPracticumSessions);
		periodLengthOfSessionsStats.calcDev(durationOfPracticumSessions);
		periodLengthOfSessionsStats.calcMin(durationOfPracticumSessions);
		periodLengthOfSessionsStats.calcMax(durationOfPracticumSessions);

		dashboard = new CompanyDashboard();
		dashboard.setNumberOfPracticaPerMonth(numberOfPracticaPerMonth);
		dashboard.setPeriodLengthOfSessionsStats(periodLengthOfSessionsStats);
		dashboard.setPeriodLengthOfPracticaStats(periodLengthOfPracticaStats);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final CompanyDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "numberOfPracticaPerMonth", "periodLengthOfSessionsStats", "periodLengthOfPracticaStats");

		super.getResponse().setData(tuple);
	}

}
