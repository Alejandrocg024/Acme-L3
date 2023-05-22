
package acme.features.company.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
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
		Double minimumLengthOfPracticumPerCompany;
		Double maximumLengthOfPracticumPerCompany;

		Principal principal;
		int companyId;

		principal = super.getRequest().getPrincipal();
		companyId = principal.getActiveRoleId();

		numberOfPracticaPerMonth = this.repository.getNumberOfPracticaPerMonth(companyId);

		averageLengthOfPracticumSessionsPerCompany = this.repository.averageLengthOfPracticumSessionsPerCompany(companyId).orElse(0.0);
		deviationLengthOfPracticumSessionsPerCompany = this.repository.deviationLengthOfPracticumSessionsPerCompany(companyId).orElse(0.0);
		minimumLengthOfPracticumSessionsPerCompany = this.repository.minimumLengthOfPracticumSessionsPerCompany(companyId).orElse(0.0);
		maximunLengthOfPracticumSessionsPerCompany = this.repository.maximumLengthOfPracticumSessionsPerCompany(companyId).orElse(0.0);

		periodLengthOfSessionsStats = new Statistic();
		periodLengthOfSessionsStats.setAverage(averageLengthOfPracticumSessionsPerCompany);
		periodLengthOfSessionsStats.setLinDev(deviationLengthOfPracticumSessionsPerCompany);
		periodLengthOfSessionsStats.setMin(minimumLengthOfPracticumSessionsPerCompany);
		periodLengthOfSessionsStats.setMax(maximunLengthOfPracticumSessionsPerCompany);

		averageLengthOfPracticumPerCompany = this.repository.averageLengthOfPracticumPerCompany(companyId).orElse(0.0);
		minimumLengthOfPracticumPerCompany = this.repository.minimumLengthOfPracticumPerCompany(companyId).orElse(0.0);
		maximumLengthOfPracticumPerCompany = this.repository.maximumLengthOfPracticumPerCompany(companyId).orElse(0.0);

		periodLengthOfPracticaStats = new Statistic();
		periodLengthOfPracticaStats.setAverage(averageLengthOfPracticumPerCompany);
		periodLengthOfPracticaStats.calcLinDev(this.repository.deviationLengthOfPracticumPerCompany(companyId));
		periodLengthOfPracticaStats.setMin(minimumLengthOfPracticumPerCompany);
		periodLengthOfPracticaStats.setMax(maximumLengthOfPracticumPerCompany);

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
