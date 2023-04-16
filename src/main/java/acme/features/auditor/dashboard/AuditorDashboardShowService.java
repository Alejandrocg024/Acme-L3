
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
import acme.forms.AuditorDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashboardRepository repository;

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
		final AuditorDashboard dashboard = new AuditorDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Auditor auditor = this.repository.findOneAuditorByUserAccountId(userAccountId);
		final double averageNumOfAuditingRecords = this.repository.findAverageNumOfAuditingRecords(auditor.getId()).orElse(0.0);
		final double maxNumOfAuditingRecords = this.repository.findMaxNumOfAuditingRecords(auditor.getId()).orElse(0.0);
		final double minNumOfAuditingRecords = this.repository.findMinNumOfAuditingRecords(auditor.getId()).orElse(0.0);
		//final double devNumOfAuditingRecords = this.repository.findLinearDevNumOfAuditingRecords(auditor.getId()).orElse(0.0);
		final Statistic auditStats = new Statistic();
		auditStats.setAverage(averageNumOfAuditingRecords);
		auditStats.setMin(minNumOfAuditingRecords);
		auditStats.setMax(maxNumOfAuditingRecords);
		//auditStats.setLinDev(devNumOfAuditingRecords);
		dashboard.setNumOfAuditingRecordsStats(auditStats);

		final Statistic periodStats = new Statistic();
		final Collection<Double> auditingRecordsPeriodOfTime = this.repository.findAuditingRecordsPeriodOfTime(auditor.getId());
		periodStats.calcAverage(auditingRecordsPeriodOfTime);
		periodStats.calcMax(auditingRecordsPeriodOfTime);
		periodStats.calcMin(auditingRecordsPeriodOfTime);
		periodStats.calcLinDev(auditingRecordsPeriodOfTime);
		dashboard.setPeriodOfAuditingRecordStats(periodStats);

		//numOfAuditsByType
		//final Map<String, Integer> auditsByNature = new HashMap<String, Integer>();
		//final Integer handsOnAudits = this.repository.findNumOfAuditsByType(auditor, Nature.HANDS_ON).orElse(0);
		//final Integer theoreticalAudits = this.repository.findNumOfAuditsByType(auditor, Nature.THEORETICAL).orElse(0);
		//auditsByNature.put("HANDS_ON", handsOnAudits);
		//auditsByNature.put("THEORETICAL", theoreticalAudits);
		//dashboard.setNumOfAuditsByType(auditsByNature);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "auditStats", "numOfAuditsByType", "periodStats");

		super.getResponse().setData(tuple);
	}

}
