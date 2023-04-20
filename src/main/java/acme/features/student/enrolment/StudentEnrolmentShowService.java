
package acme.features.student.enrolment;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Enrolment object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getStudent().getUserAccount().getId() == userAccountId);
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
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		final List<Activity> activities = this.repository.findActivitiesByEnrolmentId(object.getId()).stream().collect(Collectors.toList());
		final Double workTime = object.workTime(activities);

		choices = new SelectChoices();
		courses = this.repository.findAllPublishedCourses();
		for (final Course c : courses) {
			if (c.getId() == object.getCourse().getId()) {
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
				continue;
			}
			choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);
		}
		choices.add("0", "---", false);

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "holderName", "lowerNibble");
		tuple.put("workTime", workTime);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}
}
