
package acme.features.administrator.dashboard;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
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
		final Map<String, Statistic> currentOfferStatistic = new HashMap();
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
		statsOfUSD.calcAverage(this.repository.findPriceOfferByUSD("USD"));
		statsOfUSD.calcMax(this.repository.findPriceOfferByUSD("USD"));
		statsOfUSD.calcMin(this.repository.findPriceOfferByUSD("USD"));
		statsOfUSD.calcLinDev(this.repository.findPriceOfferByUSD("USD"));
		final Statistic statsOfEUR = new Statistic();
		statsOfEUR.calcAverage(this.repository.findPriceOfferByUSD("EUR"));
		statsOfEUR.calcMax(this.repository.findPriceOfferByUSD("EUR"));
		statsOfEUR.calcMin(this.repository.findPriceOfferByUSD("EUR"));
		statsOfEUR.calcLinDev(this.repository.findPriceOfferByUSD("EUR"));
		final Statistic statsOfGBP = new Statistic();
		statsOfGBP.calcAverage(this.repository.findPriceOfferByUSD("GBP"));
		statsOfGBP.calcMax(this.repository.findPriceOfferByUSD("GBP"));
		statsOfGBP.calcMin(this.repository.findPriceOfferByUSD("GBP"));
		statsOfGBP.calcLinDev(this.repository.findPriceOfferByUSD("GBP"));
		currentOfferStatistic.put("USD", statsOfUSD);
		currentOfferStatistic.put("EUR", statsOfEUR);
		currentOfferStatistic.put("GBP", statsOfGBP);
		dashboard = new AdministratorDashboard();
		dashboard.setPrincipalsByRole(principalsByRole);
		dashboard.setCurrentsOffersStats(currentOfferStatistic);

		final double ratioOfPeeps = this.repository.countAllPeepsWithBoth() / this.repository.countAllPeeps();
		dashboard.setPeepsRatioWithLinkAndEmail(ratioOfPeeps);
		dashboard.setRatioOfBulletinsByCriticality(ratioOfBulletinsByCriticality);

		final Date tenWeeks = (Date) MomentHelper.deltaFromCurrentMoment(-70, ChronoUnit.DAYS);
		final Collection<Note> notes = this.repository.findNotesInLast10Weeks(tenWeeks);
		final Map<Integer, Double> notasPorSemana = new HashMap<>();

		for (final Note nota : notes) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(nota.getInstantiationMoment());
			final int semana = calendar.get(Calendar.WEEK_OF_YEAR);
			notasPorSemana.put(semana, notasPorSemana.getOrDefault(semana, 0.0) + 1);
		}
		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"principalsByRole", "peepsRatioWithLinkAndEmail", "ratioOfBulletinsByCriticality", "currentsOffersStats");

		super.getResponse().setData(tuple);
	}
}
