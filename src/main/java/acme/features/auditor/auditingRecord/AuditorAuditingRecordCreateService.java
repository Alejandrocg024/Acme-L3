
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.datatypes.Mark;
import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;
	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Audit object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findAuditById(masterId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getAuditor().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		AuditingRecord object;
		object = new AuditingRecord();
		Audit audit;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findAuditById(masterId);
		object.setExceptional(!audit.isDraftMode());
		object.setDraftMode(true);
		object.setAudit(audit);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;
		super.bind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "furtherInformationLink");
	}

	@Override
	public void validate(final AuditingRecord object) {
		boolean confirmation;

		confirmation = object.getAudit().isDraftMode() || super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), object.getStartPeriod()), "startPeriod", "auditor.auditing-record.form.error.post-date");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), MomentHelper.deltaFromMoment(object.getStartPeriod(), 1, ChronoUnit.HOURS)), "endPeriod", "auditor.auditing-record.form.error.not-enough-time");
		if (!super.getBuffer().getErrors().hasErrors("subject"))
			super.state(this.auxiliarService.validateTextImput(object.getSubject()), "subject", "auditor.auditing-record.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("assessment"))
			super.state(this.auxiliarService.validateTextImput(object.getAssessment()), "assessment", "auditor.auditing-record.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(this.auxiliarService.validateDate(object.getStartPeriod()), "startPeriod", "auditor.auditing-record.form.error.before-ini");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod"))
			super.state(this.auxiliarService.validateDate(object.getEndPeriod()), "endPeriod", "auditor.auditing-record.form.error.after-end");
		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfterOrEqual(MomentHelper.getCurrentMoment(), MomentHelper.deltaFromMoment(object.getStartPeriod(), 1, ChronoUnit.HOURS)), "startPeriod", "auditor.auditing-record.form.error.one-hour-before");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			final Date minDate = new Date(100, 0, 1, 00, 00);
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), MomentHelper.deltaFromMoment(minDate, 1, ChronoUnit.HOURS)), "endPeriod", "auditor.auditing-record.form.error.min-one-hour-after");
		}

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		if (object.isExceptional())
			object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "furtherInformationLink", "exceptional", "draftMode");
		final SelectChoices choices;
		choices = SelectChoices.from(Mark.class, object.getMark());
		tuple.put("mark", choices.getSelected().getKey());
		tuple.put("marks", choices);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftModeAudit", object.getAudit().isDraftMode());
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}
}
