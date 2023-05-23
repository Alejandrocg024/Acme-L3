
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository	repository;

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
		int practicumId;
		Practicum object;
		Principal principal;

		practicumId = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();

		status = object.getCompany().getUserAccount().getId() == principal.getAccountId() && object.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "abstract$", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.findPracticumByCode(object.getCode()) == null || this.repository.findPracticumByCode(object.getCode()).equals(object), "code", "company.practicum.form.error.code");

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "company.practicum.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("abstract$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstract$()), "abstract$", "company.practicum.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("goals"))
			super.state(this.auxiliarService.validateTextImput(object.getGoals()), "goals", "company.practicum.form.error.spam");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		String estimatedTotalTime;
		Tuple tuple;

		choices = new SelectChoices();
		courses = this.repository.findAllCourses();
		estimatedTotalTime = object.estimatedTotalTime(this.repository.findPracticumSessionsByPracticumId(object.getId()));

		if (object.getCourse() == null)
			choices.add("0", "---", true);
		else
			choices.add("0", "---", false);

		for (final Course c : courses)
			if (object.getCourse() != null && object.getCourse().getId() == c.getId())
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
			else
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);

		tuple = super.unbind(object, "code", "title", "abstract$", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTotalTime", estimatedTotalTime);

		super.getResponse().setData(tuple);
	}

}
