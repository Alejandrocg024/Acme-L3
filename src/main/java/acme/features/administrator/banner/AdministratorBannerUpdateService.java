
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AuxiliarService;
import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		final int id = super.getRequest().getData("id", int.class);
		final Banner object = this.repository.findBannerById(id);
		super.getResponse().setAuthorised(MomentHelper.getCurrentMoment().before(object.getDisplayPeriodBegin()));
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
	public void bind(final Banner object) {
		assert object != null;
		super.bind(object, "displayPeriodBegin", "displayPeriodFinish", "pictureLink", "slogan", "webLink");

	}
	@Override
	public void validate(final Banner object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodBegin"))
			super.state(MomentHelper.isAfter(object.getDisplayPeriodBegin(), object.getInstantiationMoment()), "displayPeriodBegin", "administrator.banner.form.error.displayPeriodBegin");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodFinish") && !super.getBuffer().getErrors().hasErrors("displayPeriodBegin")) {
			Date maximumPeriod;
			maximumPeriod = MomentHelper.deltaFromMoment(object.getDisplayPeriodBegin(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodFinish(), maximumPeriod) && object.getDisplayPeriodFinish().after(object.getDisplayPeriodBegin()), "displayPeriodFinish", "administrator.banner.form.error.displayPeriodFinish");
		}
		if (!super.getBuffer().getErrors().hasErrors("slogan"))
			super.state(this.auxiliarService.validateTextImput(object.getSlogan()), "slogan", "administrator.banner.form.spam");

	}
	@Override
	public void perform(final Banner object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "displayPeriodBegin", "displayPeriodFinish", "pictureLink", "slogan", "webLink");
		final boolean readonly = MomentHelper.isAfterOrEqual(MomentHelper.getCurrentMoment(), object.getDisplayPeriodBegin()) && MomentHelper.isBeforeOrEqual(MomentHelper.getCurrentMoment(), object.getDisplayPeriodFinish());
		tuple.put("readonly", readonly);
		super.getResponse().setData(tuple);
	}

}
