
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumSessionId;
		Practicum practicum;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumByPracticumSessionId(practicumSessionId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "company.practicum-session.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("abstract$"))
			super.state(this.auxiliarService.validateTextImput(object.getAbstract$()), "abstract$", "company.practicum-session.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minimumStartDate), "startPeriod", "company.practicum-session.form.error.start-period");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date minimumEndDate;
			minimumEndDate = MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minimumEndDate), "endPeriod", "company.practicum-session.form.error.end-period");
		}

		if (!super.getBuffer().getErrors().hasErrors("furtherInformationLink"))
			super.state(this.auxiliarService.validateTextImput(object.getFurtherInformationLink()), "furtherInformationLink", "company.practicum-session.form.error.spam");
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "startPeriod", "endPeriod", "furtherInformationLink");

		super.getResponse().setData(tuple);
	}

}
