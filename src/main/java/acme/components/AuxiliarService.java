
package acme.components;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamFilter.SpamFilter;
import acme.entities.SystemConfiguration;
import acme.framework.components.datatypes.Money;

@Service
public class AuxiliarService {

	@Autowired
	private AuxiliarRepository repository;


	public boolean validatePrice(final Money price, final Integer minAm, final Integer maxAm) {
		final String aceptedCurrencies = this.repository.findSystemConfiguration().getAceptedCurrencies();
		final List<String> currencies = Arrays.asList(aceptedCurrencies.split(","));
		return price.getAmount() >= minAm && price.getAmount() < maxAm && currencies.contains(price.getCurrency());
	}

	public boolean validateTextImput(final String input) {
		final SystemConfiguration sc = this.repository.findSystemConfiguration();
		final SpamFilter spamFilter = new SpamFilter(sc.getSpamWords(), sc.getSpamThreshold());
		return !spamFilter.isSpam(input);
	}

}
