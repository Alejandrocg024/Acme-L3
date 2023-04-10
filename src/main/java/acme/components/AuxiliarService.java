
package acme.components;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

	public String translateBoolean(final boolean bool, final String lang) {
		String res;
		res = "";
		if (lang.equals("en"))
			res = bool ? "Yes" : "No";
		else if (lang.equals("es"))
			res = bool ? "Si" : "No";
		return res;
	}

	public String translateDate(final Date date, final String lang) {
		String res;
		res = "";
		final SimpleDateFormat spanishFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		res = spanishFormat.format(date);
		if (lang.equals("en")) {
			final SimpleDateFormat englishFormat = new SimpleDateFormat("yyyy/dd/MM hh:mm");
			res = englishFormat.format(date);
		}
		return res;
	}

	public Money translateMoney(final Money money, final String lang) {
		Money res;
		res = new Money();
		res.setAmount(money.getAmount());
		if (lang.equals("en"))
			res.setCurrency("USD");
		else if (lang.equals("es"))
			res.setCurrency("EUR");
		return res;
	}

}
