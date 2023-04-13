
package acme.features.assistant.tutorialSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Nature;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository	repository;

	@Autowired
	protected AuxiliarService						auxiliarService;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && tutorial.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int tutorialId;
		Tutorial tutorial;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		object = new TutorialSession();
		object.setTutorial(tutorial);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");
		Nature nature;
		nature = super.getRequest().getData("nature", Nature.class);
		object.setNature(nature);
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;
		boolean conditionOfCreate;

		conditionOfCreate = object.getTutorial().isDraftMode() ? false : true;
		super.state(!conditionOfCreate, "*", "assistant.tutorial.form.error.create");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "assistant.tutorial-session.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("abstract$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstract$()), "abstract$", "assistant.tutorial-session.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minimumStartDate), "startPeriod", "assistant.tutorial-session.form.error.start-period");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date minimumEndDate;
			minimumEndDate = MomentHelper.deltaFromMoment(object.getStartPeriod(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minimumEndDate), "endPeriod", "assistant.tutorial-session.form.error.end-period");
			Date maximumEndDate;
			maximumEndDate = MomentHelper.deltaFromMoment(object.getStartPeriod(), 5, ChronoUnit.HOURS);
			super.state(MomentHelper.isBeforeOrEqual(object.getEndPeriod(), maximumEndDate), "endPeriod", "assistant.tutorial-session.form.error.end-period.max");

		}

		if (!super.getBuffer().getErrors().hasErrors("furtherInformationLink"))
			super.state(this.auxiliarService.validateTextImput(object.getFurtherInformationLink()), "furtherInformationLink", "assistant.tutorial-session.form.error.spam");
	}
	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tuple tuple;
		final SelectChoices choices;
		tuple = super.unbind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);
		super.getResponse().setData(tuple);
	}

}
