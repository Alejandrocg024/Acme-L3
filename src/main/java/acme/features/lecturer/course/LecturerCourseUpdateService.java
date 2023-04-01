
package acme.features.lecturer.course;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Nature;
import acme.entities.Course;
import acme.entities.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getLecturer().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "abstract$", "price", "furtherInformationLink");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(this.auxiliarService.validatePrice(object.getPrice(), 0, 1000000), "price", "administrator.offer.form.error.price");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "lecturer.course.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("abstract$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstract$()), "abstract$", "lecturer.course.form.error.spam");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abstract$", "price", "furtherInformationLink", "draftMode", "lecturer");
		final List<Lecture> lectures = this.repository.findLecturesByCourse(object.getId()).stream().collect(Collectors.toList());
		final Nature nature = object.natureOfCourse(lectures);
		tuple.put("nature", nature);
		super.getResponse().setData(tuple);
	}
}
