
package acme.features.student.payment;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Enrolment;
import acme.forms.Payment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentPaymentCreateService extends AbstractService<Student, Payment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentPaymentRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("enrolmentId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Enrolment object;
		int id;
		id = super.getRequest().getData("enrolmentId", int.class);
		object = this.repository.findEnrolmentById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getStudent().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Payment object;
		object = new Payment();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Payment object) {
		assert object != null;
		super.bind(object, "holderName", "creditCardNumber", "expirationDate", "securityCode");
	}

	@Override
	public void validate(final Payment object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("holderName"))
			super.state(this.auxiliarService.validateTextImput(object.getHolderName()), "holderName", "student.enrolment.form.error.spam");
		if (!super.getBuffer().getErrors().hasErrors("creditCardNumber")) {
			final int cardLength = object.getCreditCardNumber().length();
			super.state(cardLength >= 12 && cardLength <= 16, "creditCardNumber", "student.enrolment.form.error.creditCardNumber");
		}
		if (!super.getBuffer().getErrors().hasErrors("expirationDate")) {
			final String locale = super.getRequest().getLocale().getLanguage();
			final String expDateString[] = object.getExpirationDate().split("/");
			final Calendar calendar = Calendar.getInstance();

			if (locale.equals("es"))
				calendar.set(Integer.parseInt(expDateString[1]), Integer.parseInt(expDateString[0]) - 1, 1, 23, 59, 59);
			else
				calendar.set(Integer.parseInt(expDateString[0]), Integer.parseInt(expDateString[1]) - 1, 1, 23, 59, 59);

			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.YEAR, 2000);
			final Date expDate = calendar.getTime();
			super.state(MomentHelper.isFuture(expDate), "expirationDate", "student.enrolment.form.error.futureDate");
		}
	}

	@Override
	public void perform(final Payment object) {
		assert object != null;
		int enrolmentId;
		final Enrolment enrolment;
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		enrolment.setHolderName(object.getHolderName());
		final String creditCardNumber = object.getCreditCardNumber();
		final String nibble = creditCardNumber.substring(creditCardNumber.length() - 4);
		enrolment.setLowerNibble(nibble);
		enrolment.setDraftMode(false);
		this.repository.save(enrolment);
	}

	@Override
	public void unbind(final Payment object) {
		assert object != null;
		Tuple tuple;
		int enrolmentId;
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);

		tuple = super.unbind(object, "holderName", "creditCardNumber", "expirationDate", "securityCode");
		tuple.put("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}
}
