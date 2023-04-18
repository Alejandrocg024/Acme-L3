
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Course;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialPublishService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


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

		status = object.getAssistant().getUserAccount().getId() == principal.getAccountId();

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
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.findTutorialByCode(object.getCode()) == null || this.repository.findTutorialByCode(object.getCode()).equals(object), "code", "assistant.tutorial.form.error.code");

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "assistant.tutorial.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("abstract$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstract$()), "abstract$", "assistant.tutorial.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("goal"))
			super.state(this.auxiliarService.validateTextImput(object.getGoal()), "goal", "assistant.tutorial.form.error.spam");

		{
			final Collection<TutorialSession> sessions = this.repository.findTutorialSessionsByTutorial(object);
			final Double totalTime = object.estimatedTotalTime(sessions);

			super.state(totalTime != null && totalTime != 0.0, "*", "assistant.tutorial.form.error.estimatedTotalTime");
		}

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstract$", "goal", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		final Collection<TutorialSession> sessions = this.repository.findTutorialSessionsByTutorial(object);
		final Double totalTime = object.estimatedTotalTime(sessions);
		tuple.put("estimatedTotalTime", totalTime);
		super.getResponse().setData(tuple);
	}
}
