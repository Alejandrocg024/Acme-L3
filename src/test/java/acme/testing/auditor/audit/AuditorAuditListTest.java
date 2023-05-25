
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String courseCode, final String courseTitle) {
		//Entramos como auditor1 y listamos todas sus audditorías, comprobando
		//que los campos que se muestran tienen los valores esperados.
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, courseCode);
		super.checkColumnHasValue(recordIndex, 2, courseTitle);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//No hay test negativo para el listado
	}

	@Test
	public void test300Hacking() {

		//Intentamos listar auditorías sin ser auditor
		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();
	}
}
