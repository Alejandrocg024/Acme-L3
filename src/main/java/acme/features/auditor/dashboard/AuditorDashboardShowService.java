
package acme.features.auditor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.forms.AuditorDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		final Map<Nature, Integer> auditsPerNature;
		final Collection<Double> numAuditingRecordsPerAudit = this.repository.findNumOfAuditingRecords(auditor.getId());
		final Statistic numAuditingStats = new Statistic();
		final Statistic periodAuditingStats = new Statistic();
		final Collection<AuditingRecord> auditingRecords;
		final Collection<Double> periodsOfAuditingRecords;

		final Collection<Audit> audits = this.repository.findAudits(auditor.getId());
		auditsPerNature = this.repository.auditsPerNature(audits);

		numAuditingStats.setAverage(this.repository.findAverageNumOfAuditingRecords(auditor.getId()).orElse(0.0));
		numAuditingStats.setMax(this.repository.findMaxNumOfAuditingRecords(auditor.getId()).orElse(0.0));
		numAuditingStats.setMin(this.repository.findMinNumOfAuditingRecords(auditor.getId()).orElse(0.0));
		numAuditingStats.calcDev(numAuditingRecordsPerAudit);

		auditingRecords = this.repository.findAuditingRecords(auditor.getId());
		periodsOfAuditingRecords = new ArrayList<>();
		for (final AuditingRecord ar : auditingRecords)
			periodsOfAuditingRecords.add(MomentHelper.computeDuration(ar.getStartPeriod(), ar.getEndPeriod()).getSeconds() / 3600.0);

		periodAuditingStats.calcAverage(periodsOfAuditingRecords);
		periodAuditingStats.calcDev(periodsOfAuditingRecords);
		periodAuditingStats.calcMin(periodsOfAuditingRecords);
		periodAuditingStats.calcMax(periodsOfAuditingRecords);

		dashboard.setNumOfAuditsByType(auditsPerNature);
		dashboard.setNumOfAuditingRecordsStats(numAuditingStats);
		dashboard.setPeriodOfAuditingRecordStats(periodAuditingStats);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "numOfAuditingRecordsStats", "numOfAuditsByType", "periodOfAuditingRecordStats");

		tuple.put("numberOfHandsOnAudits", object.getNumOfAuditsByType().get(Nature.HANDS_ON));
		tuple.put("numberOfTheoreticalAudits", object.getNumOfAuditsByType().get(Nature.THEORETICAL));
		tuple.put("numberOfBalancedAudits", object.getNumOfAuditsByType().get(Nature.BALANCED));

		super.getResponse().setData(tuple);
	}

}
