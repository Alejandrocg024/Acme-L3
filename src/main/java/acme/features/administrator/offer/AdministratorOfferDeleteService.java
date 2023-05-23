
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferDeleteService extends AbstractService<Administrator, Offer> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;

	// AbstractService interface ----------------------------------------------


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
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;
		this.repository.delete(object);
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
