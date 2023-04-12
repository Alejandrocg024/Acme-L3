
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Mark;
import acme.entities.Audit;
import acme.entities.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Audit object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getAuditor().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Audit object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;
		int courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.findAuditByCode(object.getCode()) == null || this.repository.findAuditByCode(object.getCode()).equals(object), "code", "auditor.audit.form.error.code");
		if (!super.getBuffer().getErrors().hasErrors("conclusion"))
			super.state(this.auxiliarService.validateTextImput(object.getConclusion()), "conclusion", "auditor.audit.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("strongPoints"))
			super.state(this.auxiliarService.validateTextImput(object.getStrongPoints()), "strongPoints", "auditor.audit.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("weakPoints"))
			super.state(this.auxiliarService.validateTextImput(object.getWeakPoints()), "weakPoints", "auditor.audit.form.error.spam");
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;
		Collection<Mark> marks;
		String mark;
		courses = this.repository.findCoursesNotAudited();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		marks = this.repository.findMarksByAuditId(object.getId());
		if (marks.isEmpty())
			mark = "N/A";
		else
			mark = marks.toString();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("mark", mark);
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());
		super.getResponse().setData(tuple);
	}
}
