
package acme.features.company.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
import acme.entities.Practicum;
import acme.forms.CompanyDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
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

		Double averageLengthOfPracticumSessionsPerCompany;
		Double deviationLengthOfPracticumSessionsPerCompany;
		Double minimumLengthOfPracticumSessionsPerCompany;
		Double maximunLengthOfPracticumSessionsPerCompany;

		Double averageLengthOfPracticumPerCompany;
		Double deviationLengthOfPracticumPerCompany;
		Double minimumLengthOfPracticumPerCompany;
		Double maximumLengthOfPracticumPerCompany;

		Principal principal;
		int companyId;

		principal = super.getRequest().getPrincipal();
		companyId = principal.getActiveRoleId();

		dashboard = new CompanyDashboard();
		numberOfPracticaPerMonth = new int[12];
		for (final Practicum p : this.repository.findPracticaByCompanyId(companyId)) {
			int[] monthsPerPracticum;
			monthsPerPracticum = dashboard.countMonthsPerPracticum(this.repository.findPracticumSessionsByPracticumId(p.getId()));
			for (int i = 0; i <= 11; i++)
				numberOfPracticaPerMonth[i] += monthsPerPracticum[i];
		}

		averageLengthOfPracticumSessionsPerCompany = this.repository.averageLengthOfPracticumSessionsPerCompany(companyId);
		deviationLengthOfPracticumSessionsPerCompany = this.repository.deviationLengthOfPracticumSessionsPerCompany(companyId);
		minimumLengthOfPracticumSessionsPerCompany = this.repository.minimumLengthOfPracticumSessionsPerCompany(companyId);
		maximunLengthOfPracticumSessionsPerCompany = this.repository.maximunLengthOfPracticumSessionsPerCompany(companyId);

		periodLengthOfSessionsStats = new Statistic();
		periodLengthOfSessionsStats.setAverage(averageLengthOfPracticumSessionsPerCompany);
		periodLengthOfSessionsStats.setLinDev(deviationLengthOfPracticumSessionsPerCompany);
		periodLengthOfSessionsStats.setMin(minimumLengthOfPracticumSessionsPerCompany);
		periodLengthOfSessionsStats.setMax(maximunLengthOfPracticumSessionsPerCompany);

		averageLengthOfPracticumPerCompany = this.repository.averageLengthOfPracticumPerCompany(companyId);
		deviationLengthOfPracticumPerCompany = this.repository.deviationLengthOfPracticumPerCompany(companyId);
		minimumLengthOfPracticumPerCompany = this.repository.minimumLengthOfPracticumPerCompany(companyId);
		maximumLengthOfPracticumPerCompany = this.repository.maximumLengthOfPracticumPerCompany(companyId);

		periodLengthOfPracticaStats = new Statistic();
		periodLengthOfPracticaStats.setAverage(averageLengthOfPracticumPerCompany);
		periodLengthOfPracticaStats.setLinDev(deviationLengthOfPracticumPerCompany);
		periodLengthOfPracticaStats.setMin(minimumLengthOfPracticumPerCompany);
		periodLengthOfPracticaStats.setMax(maximumLengthOfPracticumPerCompany);

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
