
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final int id = super.getRequest().getData("id", int.class);
		final Offer object = this.repository.findOfferById(id);
		super.getResponse().setAuthorised(MomentHelper.getCurrentMoment().before(object.getStartPeriod()) && MomentHelper.getCurrentMoment().before(object.getEndPeriod()) || MomentHelper.getCurrentMoment().after(object.getEndPeriod()));
	}

	@Override
	public void load() {
		Offer object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;
		super.bind(object, "endPeriod", "heading", "summary", "startPeriod", "price", "furtherInformationLink");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(this.auxiliarService.validatePrice(object.getPrice(), 0, 1000000), "price", "administrator.offer.form.error.price");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minimumStartDate), "startPeriod", "administrator.offer.form.error.startPeriod");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minimumPeriod;
			minimumPeriod = MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minimumPeriod) && object.getEndPeriod().after(object.getStartPeriod()), "endPeriod", "administrator.offer.form.error.endPeriod");
			super.state(this.auxiliarService.validateDate(object.getEndPeriod()), "endPeriod", "administrator.offer.form.error.endPeriod.oor");
			super.state(this.auxiliarService.validateDate(object.getStartPeriod()), "startPeriod", "administrator.offer.form.error.startPeriod.oor");

		}
		if (!super.getBuffer().getErrors().hasErrors("heading"))
			super.state(this.auxiliarService.validateTextImput(object.getHeading()), "heading", "administrator.offer.form.spam");
		if (!super.getBuffer().getErrors().hasErrors("summary"))
			super.state(this.auxiliarService.validateTextImput(object.getSummary()), "summary", "administrator.offer.form.spam");
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "endPeriod", "heading", "summary", "startPeriod", "price", "furtherInformationLink");
		final Offer offer = this.repository.findOfferById(object.getId());
		final boolean readonly = !(MomentHelper.getCurrentMoment().before(offer.getStartPeriod()) && MomentHelper.getCurrentMoment().before(offer.getEndPeriod()) || MomentHelper.getCurrentMoment().after(offer.getEndPeriod()));
		tuple.put("readonly", readonly);
		final boolean boton = !readonly;
		tuple.put("boton", !boton);
		tuple.put("money", this.auxiliarService.changeCurrency(object.getPrice()));
		super.getResponse().setData(tuple);
	}

}
