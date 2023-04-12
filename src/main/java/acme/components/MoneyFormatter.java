
package acme.components;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.format.Formatter;

import acme.framework.components.datatypes.Money;

public class MoneyFormatter implements Formatter<Money> {

	@Override
	public String print(final Money object, final Locale locale) {
		String res;
		res = "";
		final double parteDecimal = object.getAmount() % 1;
		final double parteEntera = object.getAmount() - parteDecimal;
		//(1,5, 3,2, etc.); sin embargo, 
		//en inglés es al revés: se escriben con un punto (1.5, 3.2, etc.).
		if (locale.getLanguage().equals("en"))
			res = parteEntera + "." + parteDecimal + " " + object.getCurrency();
		else if (locale.getLanguage().equals("es"))
			res = parteEntera + "," + parteDecimal + " " + object.getCurrency();
		return res;
	}

	@Override
	public Money parse(final String text, final Locale locale) throws ParseException {
		Money res;
		res = new Money();
		if (locale.getLanguage().equals("en"))
			res = this.convertString(text);
		else if (locale.getLanguage().equals("es"))
			res = this.convertString(text);
		return res;
	}

	private Money convertString(final String text) {
		Money res;
		res = new Money();
		final Pattern amounthPattern = Pattern.compile("(\\d+[.,]\\d+)");
		final Pattern currencyPattern = Pattern.compile("([A-Z]{3})");
		final Matcher matcherAmount = amounthPattern.matcher(text);
		final Matcher matcherCurrency = currencyPattern.matcher(text);
		final double amount = Double.parseDouble(matcherAmount.group(1).replace(',', '.'));
		final String currency = matcherCurrency.group(1);
		res.setAmount(amount);
		res.setCurrency(currency);
		return res;
	}

}
