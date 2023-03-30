
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	protected LecturerCourseRepository repository;

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
		boolean handOnLectureInCourse;
		handOnLectureInCourse = lectures.stream().anyMatch(x -> x.getNature().equals(Nature.HANDS_ON));
		assert handOnLectureInCourse;
		boolean publishedLectures;
		publishedLectures = lectures.stream().allMatch(x -> x.isDraftMode() == false);
		assert publishedLectures;
	}

	@Override
	public void perform(final Course object) {
		final Collection<Lecture> lectures = this.repository.findLecturesByCourse(object.getId());
		final Map<Nature, Integer> lecturesByType = new HashMap<>();
		for (final Lecture l : lectures) {
			final Nature nature = l.getNature();
			if (lecturesByType.containsKey(nature))
				lecturesByType.put(nature, lecturesByType.get(nature) + 1);
			else
				lecturesByType.put(nature, 1);
		}

		if (lecturesByType.get(Nature.HANDS_ON) > lecturesByType.get(Nature.THEORETICAL))
			object.setCourseType(Nature.HANDS_ON);
		else if (lecturesByType.get(Nature.HANDS_ON) < lecturesByType.get(Nature.THEORETICAL))
			object.setCourseType(Nature.THEORETICAL);
		else
			object.setCourseType(Nature.BALANCED);

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "id", "code", "title", "abstract$", "price", "furtherInformationLink", "courseType", "draftMode", "lecturer");
		super.getResponse().setData(tuple);
	}
}
