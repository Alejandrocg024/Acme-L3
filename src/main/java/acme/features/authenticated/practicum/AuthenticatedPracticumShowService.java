
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


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
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		String estimatedTotalTime;
		Tuple tuple;

		estimatedTotalTime = object.estimatedTotalTime(this.repository.findPracticumSessionsByPracticumId(object.getId()));

		tuple = super.unbind(object, "code", "title", "abstract$", "goals");
		tuple.put("company", object.getCompany().getName());
		tuple.put("estimatedTotalTime", estimatedTotalTime);

		super.getResponse().setData(tuple);
	}

}
