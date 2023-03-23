
package acme.features.any.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCoursesRepository repository;

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

		final SelectChoices choices;
		Tuple tuple;

		//choices = SelectChoices.from(AnnouncementStatus.class, object.getStatus());

		tuple = super.unbind(object, "code", "title", "abstract$", "price", "furtherInformationLink");
		tuple.put("confirmation", false);
		tuple.put("readonly", true);
		//tuple.put("statuses", choices);

		super.getResponse().setData(tuple);
	}
}
