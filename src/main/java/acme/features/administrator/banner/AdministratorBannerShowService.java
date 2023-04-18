
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "slogan", "displayPeriodBegin", "displayPeriodFinish", "pictureLink", "pictureLink", "webLink");
		tuple.put("confirmation", false);
		final Banner banner = this.repository.findBannerById(object.getId());
		final boolean readonly = !(MomentHelper.getCurrentMoment().before(banner.getDisplayPeriodBegin()) && MomentHelper.getCurrentMoment().before(banner.getDisplayPeriodFinish()) || MomentHelper.getCurrentMoment().after(banner.getDisplayPeriodFinish()));
		tuple.put("readonly", readonly);
		final boolean boton = !readonly;
		tuple.put("boton", !boton);
		super.getResponse().setData(tuple);
	}
}
