
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
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {

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
		boolean status;
		Tutorial object;
		Principal principal;
		int tutorialId;
		tutorialId = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(tutorialId);
		principal = super.getRequest().getPrincipal();
		status = object.getAssistant().getId() == principal.getActiveRoleId();
		super.getResponse().setAuthorised(status);
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
	public void bind(final Tutorial object) {
		assert object != null;
		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "code", "title", "abstract$", "goal");
		object.setCourse(course);
	}
	@Override
	public void validate(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		Collection<TutorialSession> tutorialSessions;

		tutorialSessions = this.repository.findTutorialSessionsByTutorial(object);
		this.repository.deleteAll(tutorialSessions);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstract$", "goal");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
