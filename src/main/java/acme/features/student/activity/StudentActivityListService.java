
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("enrolmentId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Enrolment object;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);
		final Principal principal = super.getRequest().getPrincipal();

		super.getResponse().setAuthorised(object.getStudent().getId() == principal.getActiveRoleId() && !object.isDraftMode());
	}

	@Override
	public void load() {
		Collection<Activity> objects;
		int enrolmentId;
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		objects = this.repository.findActivitiesByEnrolmentId(enrolmentId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "startPeriod", "endPeriod");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Activity> objects) {
		assert objects != null;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);

		super.getResponse().setGlobal("enrolmentId", enrolmentId);
	}
}
