
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
		super.bind(object, "instantiationMoment", "endPeriod", "heading", "summary", "startPeriod", "price", "furtherInformationLink");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() > 0 && object.getPrice().getAmount() < 1000000, "price", "administrator.offer.form.error.price");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getStartPeriod(), minimumStartDate), "startPeriod", "administrator.offer.form.error.startPeriod");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date maximumPeriod;
			maximumPeriod = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isBefore(object.getEndPeriod(), maximumPeriod) && object.getEndPeriod().after(object.getStartPeriod()), "endPeriod", "administrator.offer.form.error.endPeriod");
		}
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
		super.getResponse().setData(tuple);
	}

}
