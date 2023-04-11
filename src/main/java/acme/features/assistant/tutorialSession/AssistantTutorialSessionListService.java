
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Tutorial tutorial;
		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(masterId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TutorialSession> objects;
		int tutorialId;
		tutorialId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findTutorialSessionsByTutorialId(tutorialId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "startPeriod");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<TutorialSession> objects) {
		assert objects != null;
		int tutorialId;
		Tutorial tutorial;
		final boolean showCreate;
		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		showCreate = super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && !tutorial.isDraftMode();
		super.getResponse().setGlobal("masterId", tutorialId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
