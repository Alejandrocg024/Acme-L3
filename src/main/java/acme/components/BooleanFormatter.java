
package acme.components;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class BooleanFormatter implements Formatter<Boolean> {

	@Override
	public String print(final Boolean object, final Locale locale) {
		String res;
		res = "";
		if (locale.getLanguage().equals("en"))
			res = object ? "Yes" : "No";
		else if (locale.getLanguage().equals("es"))
			res = object ? "Si" : "No";
		return res;
	}

	@Override
	public Boolean parse(final String text, final Locale locale) throws ParseException {
		final Boolean res;
		if (!(text.equals("Yes") || text.equals("No") || text.equals("Si"))) {
			String errorMessage;
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locale);
			throw new ParseException(errorMessage, 0);
		}
		if (text.equals("Yes") || text.equals("Si"))
			res = true;
		else
			res = false;
		return res;
	}

}
