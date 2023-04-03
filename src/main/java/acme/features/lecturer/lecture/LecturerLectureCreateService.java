
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Nature;
import acme.entities.Course;
import acme.entities.CourseLecture;
import acme.entities.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

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
		Lecture object;
		object = new Lecture();
		object.setDraftMode(true);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "summary", "estimatedLearningTime", "body", "nature", "furtherInformationLink");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedLearningTime"))
			super.state(object.getEstimatedLearningTime() >= 0.01, "estimatedLearningTime", "lecturer.lecture.form.error.estimatedLearningTIme");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "lecturer.lecture.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("summary"))
			super.state(this.auxiliarService.validateTextImput(object.getSummary()), "summary", "lecturer.lecture.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("body"))
			super.state(this.auxiliarService.validateTextImput(object.getBody()), "body", "lecturer.lecture.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("nature"))
			super.state(!object.getNature().equals(Nature.BALANCED), "nature", "lecturer.lecture.form.error.nature");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		this.repository.save(object);
		CourseLecture lc;
		lc = new CourseLecture();
		lc.setLecture(object);
		Course course;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findCourseById(masterId);
		lc.setCourse(course);
		this.repository.save(lc);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "summary", "estimatedLearningTime", "body", "nature", "furtherInformationLink", "draftMode");
		final SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		super.getResponse().setData(tuple);
	}
}
