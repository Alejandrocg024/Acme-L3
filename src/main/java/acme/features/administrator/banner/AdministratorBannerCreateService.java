
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
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository	repository;

	@Autowired
	protected AuxiliarService				auxiliarService;


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
		Banner object;
		object = new Banner();
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(actualDate);
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
			super.state(this.auxiliarService.validateDate(object.getDisplayPeriodFinish()), "displayPeriodFinish", "administrator.bulletin.form.error.endPeriod.oor");
			super.state(this.auxiliarService.validateDate(object.getDisplayPeriodBegin()), "displayPeriodBegin", "administrator.bulletin.form.error.startPeriod.oor");
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
		super.getResponse().setData(tuple);

	}
}
