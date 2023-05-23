
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


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
		Note object;
		Date moment;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		moment = MomentHelper.getCurrentMoment();

		object = new Note();
		object.setInstantiationMoment(moment);
		object.setAuthor(userAccount.getUsername() + "-" + userAccount.getIdentity().getSurname() + ", " + userAccount.getIdentity().getName());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "title", "author", "message", "email", "furtherInformationLink");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "authenticated.note.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("author"))
			super.state(this.auxiliarService.validateTextImput(object.getAuthor()), "author", "authenticated.note.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("message"))
			super.state(this.auxiliarService.validateTextImput(object.getMessage()), "message", "authenticated.note.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("email"))
			super.state(this.auxiliarService.validateTextImput(object.getEmail()), "email", "authenticated.note.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("furtherInformationLink"))
			super.state(this.auxiliarService.validateTextImput(object.getFurtherInformationLink()), "furtherInformationLink", "authenticated.note.form.error.spam");
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "author", "message", "email", "furtherInformationLink");
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}

}
