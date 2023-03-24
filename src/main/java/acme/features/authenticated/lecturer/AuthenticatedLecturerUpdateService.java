
package acme.features.authenticated.lecturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamFilter.SpamFilter;
import acme.entities.SystemConfiguration;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class AuthenticatedLecturerUpdateService extends AbstractService<Authenticated, Lecturer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedLecturerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecturer object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneEmployerByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecturer object) {
		assert object != null;

		super.bind(object, "almaMater", "resume", "listOfQualifications", "furtherInformation");
	}

	@Override
	public void validate(final Lecturer object) {

		assert object != null;

		final SystemConfiguration config = this.repository.findSystemConfiguration();

		final SpamFilter spamFilter = new SpamFilter(config.getSpamWords(), config.getSpamThreshold());

		if (!super.getBuffer().getErrors().hasErrors("almaMater")) {
			final String almaMater = super.getRequest().getData("almaMater", String.class);
			boolean validAlmaMater;
			validAlmaMater = spamFilter.isSpam(almaMater);
			if (validAlmaMater)
				super.state(validAlmaMater, "almaMater", "authenticated.lecturer.form.error.almaMater");
		}

		if (!super.getBuffer().getErrors().hasErrors("listOfQualifications")) {

			final boolean validListOfQualifications = spamFilter.isSpam(object.getListOfQualifications());
			if (validListOfQualifications)
				super.state(validListOfQualifications, "listOfQualifications", "authenticated.lecturer.form.error.listOfQualifications");
		}
		if (!super.getBuffer().getErrors().hasErrors("resume")) {

			final boolean validResume = spamFilter.isSpam(object.getResume());
			if (validResume)
				super.state(validResume, "resume", "authenticated.lecturer.form.error.resume");
		}

	}

	@Override
	public void perform(final Lecturer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecturer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "almaMater", "resume", "listOfQualifications", "furtherInformation");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
