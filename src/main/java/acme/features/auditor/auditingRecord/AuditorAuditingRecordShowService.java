
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Mark;
import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordShowService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Audit object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getAuditor().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choice;
		choice = SelectChoices.from(Mark.class, object.getMark());
		tuple = super.unbind(object, "subject", "assesment", "startPeriod", "endPeriod", "mark", "furtherInformationLink");
		tuple.put("masterId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());
		tuple.put("course", choice.getSelected().getKey());
		tuple.put("courses", choice);
		super.getResponse().setData(tuple);

	}
}
