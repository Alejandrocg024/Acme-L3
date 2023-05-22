
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
public class AssistantTutorialSessionShowService extends AbstractService<Assistant, TutorialSession> {

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
		final boolean status;
		final int tutorialSessionId;
		final Tutorial object;
		tutorialSessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialByTutorialSessionId(tutorialSessionId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		status = object != null && object.getAssistant().getUserAccount().getId() == userAccountId;
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
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tuple tuple;
		final SelectChoices choices;
		tuple = super.unbind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");
		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);
		tuple.put("masterId", object.getTutorial().getId());
		super.getResponse().setData(tuple);
	}
}
