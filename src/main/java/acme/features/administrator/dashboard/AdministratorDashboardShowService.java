
package acme.features.administrator.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AdministratorDashboard;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


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
		AdministratorDashboard dashboard;
		final Map<String, Integer> principalsByRole = new HashMap();
		final Integer lecturerPrincipals = this.repository.countPrincipalByLecturer();
		final Integer assistantPrincipals = this.repository.countPrincipalByAssistant();
		final Integer providerPrincipals = this.repository.countPrincipalByProvider();
		final Integer companyPrincipals = this.repository.countPrincipalByCompany();
		final Integer consumerPrincipals = this.repository.countPrincipalByConsumer();
		final Integer studentPrincipals = this.repository.countPrincipalByStudent();
		final Integer auditorPrincipals = this.repository.countPrincipalByAuditor();
		final Integer adminPrincipals = this.repository.countPrincipalByAdministrator();
		principalsByRole.put("Lecturer", lecturerPrincipals);
		principalsByRole.put("Assistant", assistantPrincipals);
		principalsByRole.put("Provider", providerPrincipals);
		principalsByRole.put("Company", companyPrincipals);
		principalsByRole.put("Consumer", consumerPrincipals);
		principalsByRole.put("Student", studentPrincipals);
		principalsByRole.put("Auditor", auditorPrincipals);
		principalsByRole.put("Administrator", adminPrincipals);
		dashboard = new AdministratorDashboard();
		dashboard.setPrincipalsByRole(principalsByRole);
		final double ratioOfPeeps = this.repository.countAllPeepsWithBoth() / this.repository.countAllPeeps();
		dashboard.setPeepsRatioWithLinkAndEmail(ratioOfPeeps);
		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"principalsByRole", "peepsRatioWithLinkAndEmail");

		super.getResponse().setData(tuple);
	}
}
