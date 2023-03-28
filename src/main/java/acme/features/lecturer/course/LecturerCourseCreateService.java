
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		object = new Course();
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "id", "code", "title", "abstract$", "price", "furtherInformationLink");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() > 0 && object.getPrice().getAmount() < 1000000, "price", "administrator.offer.form.error.price");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		//object.setInstantiationMoment(new Date());
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "id", "code", "title", "abstract$", "price", "furtherInformationLink");
		super.getResponse().setData(tuple);
	}
}
