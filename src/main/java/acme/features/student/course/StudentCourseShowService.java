
package acme.features.student.course;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Nature;
import acme.entities.Course;
import acme.entities.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseRepository	repository;

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
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getResponse().setAuthorised(!object.isDraftMode());
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
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Lecturer lecturer;
		List<Lecture> lectures;
		final Nature nature;

		tuple = super.unbind(object, "code", "title", "abstract$", "price", "furtherInformationLink");
		lectures = this.repository.findLecturesByCourseId(object.getId()).stream().collect(Collectors.toList());
		nature = object.natureOfCourse(lectures);
		tuple.put("nature", nature);
		tuple.put("money", this.auxiliarService.changeCurrency(object.getPrice()));

		lecturer = object.getLecturer();
		tuple.put("almaMater", lecturer.getAlmaMater());
		tuple.put("resume", lecturer.getResume());
		tuple.put("listOfQualifications", lecturer.getListOfQualifications());
		tuple.put("furtherInformationLink", lecturer.getFurtherInformation());

		super.getResponse().setData(tuple);
	}
}
