
package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferRepository	repository;

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
		Offer object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);
		super.getResponse().setAuthorised(MomentHelper.getCurrentMoment().after(object.getStartPeriod()) //
			&& MomentHelper.getCurrentMoment().before(object.getEndPeriod()));
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
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "startPeriod", "endPeriod", "price", "furtherInformationLink");
		tuple.put("money", this.auxiliarService.changeCurrency(object.getPrice()));
		super.getResponse().setData(tuple);
	}

}
