
package acme.features.administrator.dashboard;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
import acme.entities.Note;
import acme.forms.AdministratorDashboard;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		final Map<Boolean, Double> ratioOfBulletinsByCriticality = new HashMap();
		final Map<String, Statistic> offersByCurrencyStats = new HashMap();
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
		ratioOfBulletinsByCriticality.put(true, this.repository.countAllCriticalBulletin() / this.repository.countAllBulletin());
		ratioOfBulletinsByCriticality.put(false, this.repository.countAllNonCriticalBulletin() / this.repository.countAllBulletin());

		final Statistic statsOfUSD = new Statistic();
		statsOfUSD.setCount(this.repository.findPriceOfferByCurrency("USD").size());
		statsOfUSD.calcAverage(this.repository.findPriceOfferByCurrency("USD"));
		statsOfUSD.calcMax(this.repository.findPriceOfferByCurrency("USD"));
		statsOfUSD.calcMin(this.repository.findPriceOfferByCurrency("USD"));
		statsOfUSD.calcLinDev(this.repository.findPriceOfferByCurrency("USD"));
		final Statistic statsOfEUR = new Statistic();
		statsOfEUR.setCount(this.repository.findPriceOfferByCurrency("EUR").size());
		statsOfEUR.calcAverage(this.repository.findPriceOfferByCurrency("EUR"));
		statsOfEUR.calcMax(this.repository.findPriceOfferByCurrency("EUR"));
		statsOfEUR.calcMin(this.repository.findPriceOfferByCurrency("EUR"));
		statsOfEUR.calcLinDev(this.repository.findPriceOfferByCurrency("EUR"));
		final Statistic statsOfGBP = new Statistic();
		statsOfGBP.setCount(this.repository.findPriceOfferByCurrency("GBP").size());
		statsOfGBP.calcAverage(this.repository.findPriceOfferByCurrency("GBP"));
		statsOfGBP.calcMax(this.repository.findPriceOfferByCurrency("GBP"));
		statsOfGBP.calcMin(this.repository.findPriceOfferByCurrency("GBP"));
		statsOfGBP.calcLinDev(this.repository.findPriceOfferByCurrency("GBP"));
		offersByCurrencyStats.put("USD", statsOfUSD);
		offersByCurrencyStats.put("EUR", statsOfEUR);
		offersByCurrencyStats.put("GBP", statsOfGBP);
		dashboard = new AdministratorDashboard();
		dashboard.setPrincipalsByRole(principalsByRole);
		dashboard.setOffersByCurrencyStats(offersByCurrencyStats);

		final double ratioOfPeeps = this.repository.countAllPeepsWithBoth() / this.repository.countAllPeeps();
		dashboard.setPeepsRatioWithLinkAndEmail(ratioOfPeeps);
		dashboard.setRatioOfBulletinsByCriticality(ratioOfBulletinsByCriticality);

		final Date tenWeeks = MomentHelper.deltaFromCurrentMoment(-70, ChronoUnit.DAYS);
		final Collection<Note> notes = this.repository.findNotesInLast10Weeks(tenWeeks);
		final Map<Integer, Double> notasPorSemana = new HashMap<>();

		for (final Note nota : notes) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(nota.getInstantiationMoment());
			final int semana = calendar.get(Calendar.WEEK_OF_YEAR);
			notasPorSemana.put(semana, notasPorSemana.getOrDefault(semana, 0.0) + 1);
		}
		dashboard.setNotesInLast10WeeksStats(dashboard.calcNotesInLast10WeeksStats(notasPorSemana));

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "principalsByRole", "peepsRatioWithLinkAndEmail", "ratioOfBulletinsByCriticality", "offersByCurrencyStats", "notesInLast10WeeksStats");

		super.getResponse().setData(tuple);
	}
}
