
package acme.features.assistant.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	@Autowired
	protected AssistantDashboardRepository	repository;

	private Collection<Double>				durationOfTutorials;

	private Collection<Double>				durationOfTutorialSessions;


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
		final AssistantDashboard dashboard;

		final Assistant assistant = this.repository.findAssitantByUserAccountId(super.getRequest().getPrincipal().getAccountId());
		final Collection<Tutorial> tutorialPerAssistant = this.repository.findTutorialsByAssistant(assistant);
		final Map<Nature, Integer> aux = this.repository.tutorialPerNature(tutorialPerAssistant);
		final Map<String, Integer> tutorialPerNature = new HashMap<>();
		for (final Nature a : aux.keySet()) {
			if (a.equals(Nature.THEORETICAL))
				tutorialPerNature.put("THEORETICAL", aux.get(Nature.THEORETICAL));
			if (a.equals(Nature.HANDS_ON))
				tutorialPerNature.put("HANDS_ON", aux.get(Nature.HANDS_ON));
			if (a.equals(Nature.BALANCED))
				tutorialPerNature.put("BALANCED", aux.get(Nature.BALANCED));
		}
		dashboard = new AssistantDashboard();
		dashboard.setNumOfTutorialsByType(tutorialPerNature);
		final Statistic statsOfTutorial = new Statistic();
		this.durationOfTutorials = new ArrayList<Double>();
		for (final Tutorial t : tutorialPerAssistant) {
			final Tutorial auxt = this.repository.findTutorialById(t.getId());
			if (this.repository.findTutorialSessionsByTutorial(auxt) != null)
				this.durationOfTutorials.add(auxt.estimatedTotalTime(this.repository.findTutorialSessionsByTutorial(auxt)));
			else
				this.durationOfTutorials.add(0.0);
		}
		statsOfTutorial.calcAverage(this.durationOfTutorials);
		statsOfTutorial.calcMax(this.durationOfTutorials);
		statsOfTutorial.calcMin(this.durationOfTutorials);
		statsOfTutorial.calcLinDev(this.durationOfTutorials);
		statsOfTutorial.setCount(this.durationOfTutorials.size());
		dashboard.setTimeOfTutorialsStats(statsOfTutorial);
		final Statistic statsOfTutorialSessions = new Statistic();
		this.durationOfTutorialSessions = new ArrayList<Double>();
		for (final TutorialSession ts : this.repository.findTutorialSessionsByAssistant(assistant)) {
			final Tutorial t = new Tutorial();
			final List<TutorialSession> auxl = new ArrayList<>();
			auxl.add(ts);
			this.durationOfTutorialSessions.add(t.estimatedTotalTime(auxl));
		}
		statsOfTutorialSessions.calcAverage(this.durationOfTutorialSessions);
		statsOfTutorialSessions.calcMax(this.durationOfTutorialSessions);
		statsOfTutorialSessions.calcMin(this.durationOfTutorialSessions);
		statsOfTutorialSessions.calcLinDev(this.durationOfTutorialSessions);
		statsOfTutorialSessions.setCount(this.durationOfTutorialSessions.size());
		dashboard.setTimeOfSessionsStats(statsOfTutorialSessions);
		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "numOfTutorialsByType", "timeOfSessionsStats", "timeOfTutorialsStats");

		super.getResponse().setData(tuple);
	}

}
