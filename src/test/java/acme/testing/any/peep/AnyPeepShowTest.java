
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepShowTest extends TestHarness {

	@Test
	public void test100Positive() {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2020/01/02 11:00");
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2020/01/02 11:00");
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("company1", "company1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("student1", "student1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2020/01/02 11:00");
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2020/01/02 11:00");
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("administrator", "administrator");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2020/01/02 11:00");
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2020/01/02 11:00");
		super.checkInputBoxHasValue("title", "<h1>Hola Mundo!</h1>");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test101Positive(final int recordIndex, final String title, final String nick, final String message, final String IM, final String email, final String link) {
		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("instantiationMoment", IM);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);
	}

	public void test200Negative() {
		//No hay test negativos para el listado
	}

	public void test300Hacking() {
		//No hay casos de hacking
	}
}
