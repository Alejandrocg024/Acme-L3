
package acme.features.student.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureListService extends AbstractService<Student, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Course object;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		object = this.repository.findCourseById(courseId);
		status = !object.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findLecturesByCourseId(courseId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "estimatedLearningTime");

		super.getResponse().setData(tuple);
	}
	@Override
	public void unbind(final Collection<Lecture> objects) {
		assert objects != null;

		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);

		super.getResponse().setGlobal("courseId", courseId);
	}

}
