
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String nick, final String message, final String email, final String link) {
		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);
	}

	@Test
	public void test101Positive() {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<g");
		super.fillInputBoxIn("message", "hola");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<g");
		super.checkInputBoxHasValue("nick", "Begines, Guaje");
		super.checkInputBoxHasValue("message", "hola");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
	}

	@Test
	public void test102Positive() {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<a");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(1);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<a");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("company1", "company1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<b");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(2);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<b");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("student1", "student1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<c");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(3);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<c");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<d");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(4);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<d");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.signIn("administrator", "administrator");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<e");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(5);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<e");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
		super.signOut();

		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", "<f");
		super.fillInputBoxIn("nick", "Juan");
		super.fillInputBoxIn("message", "Este es le primer peep del sistema");
		super.fillInputBoxIn("email", "");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("instantiationMoment", "2022/07/30 00:00");
		super.checkInputBoxHasValue("title", "<f");
		super.checkInputBoxHasValue("nick", "Juan");
		super.checkInputBoxHasValue("message", "Este es le primer peep del sistema");
		super.checkInputBoxHasValue("email", "");
		super.checkInputBoxHasValue("link", "");
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String nick, final String message, final String email, final String link) {
		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnButton("Publish");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();

	}

	public void test300Hacking() {
		//No hay hacking ya que cualquiera puede crear peeps
	}

}
