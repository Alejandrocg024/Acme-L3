
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;
		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByTutorialSessionId(tutorialId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		status = tutorial != null && tutorial.getAssistant().getUserAccount().getId() == userAccountId && tutorial.isDraftMode();
		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		TutorialSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialSessionById(id);
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
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.delete(object);
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
