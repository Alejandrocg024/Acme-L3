
package acme.features.authenticated.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentUpdateService extends AbstractService<Authenticated, Student> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentRepository	repository;

	@Autowired
	protected AuxiliarService					auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Student object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findStudentByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Student object) {
		assert object != null;

		super.bind(object, "statement", "strongFeatures", "weakFeatures", "furtherInformationLink");
	}

	@Override
	public void validate(final Student object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("statement"))
			super.state(this.auxiliarService.validateTextImput(object.getStatement()), "statement", "authenticated.student.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("strongFeatures"))
			super.state(this.auxiliarService.validateTextImput(object.getStrongFeatures()), "strongFeatures", "authenticated.student.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("weakFeatures"))
			super.state(this.auxiliarService.validateTextImput(object.getWeakFeatures()), "weakFeatures", "authenticated.student.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("furtherInformationLink"))
			super.state(this.auxiliarService.validateTextImput(object.getFurtherInformationLink()), "furtherInformationLink", "authenticated.student.form.error.spam");
	}

	@Override
	public void perform(final Student object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Student object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "statement", "strongFeatures", "weakFeatures", "furtherInformationLink");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
