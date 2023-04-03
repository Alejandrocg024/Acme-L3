
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseLecture;
import acme.entities.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureCreate extends AbstractService<Lecturer, CourseLecture> {

	@Autowired
	protected LecturerCourseLecturerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Course object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findCourseById(masterId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getLecturer().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		CourseLecture object;
		object = new CourseLecture();
		Course course;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findCourseById(masterId);
		object.setCourse(course);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		assert object != null;
		super.bind(object, "lecture");
	}

	@Override
	public void validate(final CourseLecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("lecture")) {
			int masterId;
			masterId = super.getRequest().getData("masterId", int.class);
			final Collection<Lecture> lectures = this.repository.findLecturesByCourse(masterId);
			super.state(!lectures.contains(object.getLecture()), "lecture", "lecturer.courseLecture.form.error.lecture");
		}

	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "lecture", "course");
		final int masterId = super.getRequest().getData("masterId", int.class);
		final Lecturer lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		Collection<Lecture> lectures;
		lectures = this.repository.findLecturesByLecturer(lecturer);
		final Course course = this.repository.findCourseById(masterId);
		final SelectChoices choices;
		choices = SelectChoices.from(lectures, "title", object.getLecture());
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", course.isDraftMode());
		super.getResponse().setData(tuple);
	}

}
