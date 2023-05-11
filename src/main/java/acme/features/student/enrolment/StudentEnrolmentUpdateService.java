
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Enrolment object;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);
		final Principal principal = super.getRequest().getPrincipal();

		super.getResponse().setAuthorised(object.getStudent().getId() == principal.getActiveRoleId() && object.isDraftMode());
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "motivation", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("motivation"))
			super.state(this.auxiliarService.validateTextImput(object.getMotivation()), "motivation", "student.enrolment.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("goals"))
			super.state(this.auxiliarService.validateTextImput(object.getGoals()), "goals", "student.enrolment.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;
			existing = this.repository.findEnrolmentByCode(object.getCode());
			final Enrolment enrolment = this.repository.findEnrolmentById(object.getId());
			super.state(existing == null || enrolment.equals(existing), "code", "lecturer.course.form.error.code");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		courses = this.repository.findAllPublishedCourses();

		if (object.getCourse() == null)
			choices.add("0", "---", true);
		else
			choices.add("0", "---", false);

		for (final Course c : courses)
			if (object.getCourse() != null && object.getCourse().getId() == c.getId())
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
			else
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
