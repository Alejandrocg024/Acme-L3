
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String nick, final String message) {
		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, nick);
		super.checkColumnHasValue(recordIndex, 2, message);
	}

	@Test
	public void test101Positive() {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
		super.signOut();

		super.signIn("company1", "company1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
		super.signOut();

		super.signIn("student1", "student1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
		super.signOut();

		super.signIn("administrator", "administrator");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
		super.signOut();

		super.checkLinkExists("Sign in");
		super.clickOnMenu("Any", "Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(0, 0, "<h1>Hola Mundo!</h1>");
		super.checkColumnHasValue(0, 1, "Juan");
		super.checkColumnHasValue(0, 2, "Este es le primer peep del sistema");
	}

	@Test
	public void test200Negative() {
		//No hay test negativos para el listado
	}

	@Test
	public void test300Hacking() {
		//No hay casos de hacking
	}
}
