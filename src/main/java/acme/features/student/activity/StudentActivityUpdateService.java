
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Nature;
import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

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
		object = this.repository.findEnrolmentByActivityId(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getStudent().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		super.bind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");
		Nature nature;
		nature = super.getRequest().getData("nature", Nature.class);
		object.setNature(nature);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "student.activity.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("abstract$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstract$()), "abstract$", "student.activity.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(this.auxiliarService.validateDate(object.getStartPeriod()), "startPeriod", "student.activity.form.error.dates");

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "student.activity.form.error.period");
			super.state(this.auxiliarService.validateDate(object.getEndPeriod()), "endPeriod", "student.activity.form.error.dates");

		}

		if (!super.getBuffer().getErrors().hasErrors("furtherInformationLink"))
			super.state(this.auxiliarService.validateTextImput(object.getFurtherInformationLink()), "furtherInformationLink", "student.activity.form.error.spam");
	}
	@Override
	public void perform(final Activity object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple = super.unbind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);
		super.getResponse().setData(tuple);
	}
}
