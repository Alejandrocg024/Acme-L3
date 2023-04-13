
package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepPublishService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository	repository;

	@Autowired
	protected AuxiliarService	auxiliarService;


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
		Peep object;
		Date moment;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		moment = MomentHelper.getCurrentMoment();

		object = new Peep();
		object.setInstantiationMoment(moment);
		object.setNick(principal.isAnonymous() ? "" : String.format("%s %s", userAccount.getIdentity().getName(), userAccount.getIdentity().getSurname()));

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "any.peep.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("nick"))
			super.state(this.auxiliarService.validateTextImput(object.getNick()), "nick", "any.peep.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("message"))
			super.state(this.auxiliarService.validateTextImput(object.getMessage()), "message", "any.peep.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("email"))
			super.state(this.auxiliarService.validateTextImput(object.getEmail()), "email", "any.peep.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("link"))
			super.state(this.auxiliarService.validateTextImput(object.getLink()), "link", "any.peep.form.error.spam");
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "nick", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}
