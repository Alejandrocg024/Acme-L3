
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinPostService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;

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
		Bulletin object;
		object = new Bulletin();
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(actualDate);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;
		super.bind(object, "instantiationMoment", "title", "message", "critical", "furtherInformationLink");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;
		boolean confirmation;
		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(this.auxiliarService.validateTextImput(object.getTitle()), "title", "administrator.bulletin.form.spam");
		if (!super.getBuffer().getErrors().hasErrors("message"))
			super.state(this.auxiliarService.validateTextImput(object.getMessage()), "message", "administrator.bulletin.form.spam");

	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "message", "critical", "furtherInformationLink");
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);

	}
}
