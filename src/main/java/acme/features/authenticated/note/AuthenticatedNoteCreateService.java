
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	protected AuthenticatedNoteRepository repository;


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
		/*
		 * object.setTitle("");
		 * object.setAuthor("");
		 * object.setMessage("");
		 * object.setEmail("");
		 * object.setFurtherInformationLink("");
		 */

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "title", "author", "message", "email", "furtherInformationLink");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
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

		tuple = super.unbind(object, "instantiationMoment", "title", "author", "message", "email", "furtherInformationLink");
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}

}
