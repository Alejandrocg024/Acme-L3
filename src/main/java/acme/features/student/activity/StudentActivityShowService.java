
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
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

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
		super.getResponse().setAuthorised(object.getStudent().getUserAccount().getId() == userAccountId && !object.isDraftMode());
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
