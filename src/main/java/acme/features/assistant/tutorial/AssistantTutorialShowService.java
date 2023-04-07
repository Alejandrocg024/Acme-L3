
package acme.features.assistant.tutorial;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Tutorial object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getAssistant().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abstract$", "goal", "draftMode", "course", "assistant");
		final List<TutorialSession> sessions = this.repository.findTutorialSessionsByTutorial(object).stream().collect(Collectors.toList());
		final Double totalTime = object.estimatedTotalTime(sessions);
		tuple.put("estimatedTotalTime", totalTime);
		super.getResponse().setData(tuple);
	}
}
