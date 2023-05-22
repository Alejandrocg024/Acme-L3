
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
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
		super.getResponse().setAuthorised(object.getAssistant().getUserAccount().getId() == principal.getAccountId());
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
		final Collection<Course> courses;
		final SelectChoices choices = new SelectChoices();

		tuple = super.unbind(object, "code", "title", "abstract$", "goal", "draftMode");
		final Collection<TutorialSession> sessions = this.repository.findTutorialSessionsByTutorial(object);
		final Double totalTime = object.estimatedTotalTime(sessions);
		tuple.put("estimatedTotalTime", totalTime);
		courses = this.repository.findAllPublishedCourses();
		for (final Course c : courses) {
			if (c.getId() == object.getCourse().getId()) {
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
				continue;
			}
			choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);
		}

		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}
}
