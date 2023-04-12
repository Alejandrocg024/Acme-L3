
package acme.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date> {

	@Override
	public String print(final Date object, final Locale locale) {
		String res;
		res = "";
		if (locale.getLanguage().equals("es")) {
			final SimpleDateFormat spanishFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			res = spanishFormat.format(object);
		}

		if (locale.getLanguage().equals("en")) {
			final SimpleDateFormat englishFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
			res = englishFormat.format(object);
		}
		return res;
	}

	@Override
	public Date parse(final String text, final Locale locale) throws ParseException {
		Date res;
		res = new Date();

		if (locale.getLanguage().equals("en")) {
			final SimpleDateFormat englishFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
			res = englishFormat.parse(text);
		}
		if (locale.getLanguage().equals("es")) {
			final SimpleDateFormat englishFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			res = englishFormat.parse(text);
		}
		return res;
	}

}
