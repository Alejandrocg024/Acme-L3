
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Nature;
import acme.entities.Lecture;
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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Lecture object;
		object = new Lecture();
		object.setDraftMode(true);
		final Lecturer lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setLecturer(lecturer);
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
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "summary", "estimatedLearningTime", "body", "nature", "furtherInformationLink", "draftMode", "lecturer");
		final SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);
		super.getResponse().setData(tuple);
	}
}
