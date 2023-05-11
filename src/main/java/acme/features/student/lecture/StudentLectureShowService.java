
package acme.features.student.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.entities.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureShowService extends AbstractService<Student, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findLectureById(id);
		status = !object.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final SelectChoices natureChoices;
		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "estimatedLearningTime", "body", "nature", "furtherInformationLink");
		natureChoices = SelectChoices.from(Nature.class, object.getNature());
		tuple.put("nature", natureChoices.getSelected().getKey());

		super.getResponse().setData(tuple);
	}
}
