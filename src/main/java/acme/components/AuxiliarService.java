
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


	public boolean validatePrice(final Money price) {
		final String aceptedCurrencies = this.repository.findSystemConfiguration().getAceptedCurrencies();
		final List<String> currencies = Arrays.asList(aceptedCurrencies.split(","));
		return price.getAmount() >= 0 && price.getAmount() < 1000000 && currencies.contains(price.getCurrency());
	}

	public boolean validateTextImput(final String input) {
		final SystemConfiguration sc = this.repository.findSystemConfiguration();
		final SpamFilter spamFilter = new SpamFilter(sc.getSpamWords(), sc.getSpamThreshold());
		return !spamFilter.isSpam(input);
	}

}
