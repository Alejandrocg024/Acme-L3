
package acme.features.any.peep;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepListService extends AbstractService<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Collection<Peep> objects;
		Date date;

		date = new Date();
		objects = this.repository.findPeepBeforeDate(date);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "nick", "message");

		super.getResponse().setData(tuple);
	}
}
