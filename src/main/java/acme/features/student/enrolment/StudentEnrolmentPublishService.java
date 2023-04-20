
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
public class StudentEnrolmentPublishService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository	repository;

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

		super.bind(object, "code", "motivation", "goals", "holderName");
		object.setCourse(course);

	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("holderName")) {
			//super.state(!object.getHolderName().isEmpty(), "holderName", "student.enrolment.form.error.emptyHolderName");
			//super.state(this.auxiliarService.validateTextImput(object.getHolderName()), "holderName", "student.enrolment.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("creditCardNumber")) {
			//debe pasar las comprobaciones de @CreditCardNumber
		}
		if (!super.getBuffer().getErrors().hasErrors("expirationDate")) {
			//Procesar fecha como patrón "\d{2}/\d{2}" y procesarla si MM/YY o YY/MM (23:59 del último día del mes/año indicado). Comprobar que esté en futuro
			//super.state(MomentHelper.isFuture(object.getExpirationDate), "expirationDate", "student.enrolment.form.error.expirationDate");
		}
		if (!super.getBuffer().getErrors().hasErrors("securityCode")) {
			//tres digitos
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
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
