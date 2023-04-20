
package acme.features.lecturer.course;

import java.util.Collection;
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
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

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
		final Collection<Lecture> lectures = this.repository.findLecturesByCourse(object.getId());
		super.state(!lectures.isEmpty(), "*", "lecturer.course.form.error.nolecture");
		if (!lectures.isEmpty()) {
			boolean handOnLectureInCourse;
			handOnLectureInCourse = lectures.stream().anyMatch(x -> x.getNature().equals(Nature.HANDS_ON));
			super.state(handOnLectureInCourse, "*", "lecturer.course.form.error.nohandson");
			boolean publishedLectures;
			publishedLectures = lectures.stream().allMatch(x -> x.isDraftMode() == false);
			super.state(publishedLectures, "*", "lecturer.course.form.error.lecturenp");
		}
	}

	@Override
	public void perform(final Course object) {
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abstract$", "price", "furtherInformationLink", "draftMode");
		final List<Lecture> lectures = this.repository.findLecturesByCourse(object.getId()).stream().collect(Collectors.toList());
		final Nature nature = object.natureOfCourse(lectures);
		tuple.put("hasLectures", lectures.size() > 0);
		tuple.put("nature", nature);
		tuple.put("money", this.auxiliarService.changeCurrency(object.getPrice()));
		super.getResponse().setData(tuple);
	}
}
