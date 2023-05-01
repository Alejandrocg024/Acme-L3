
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String course, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String mark) {
		// Este test autentica a un auditor, lista sus auditorías y crea una nueva,
		// confirmando que se ha creado de manera correcta.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, course);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("mark", mark);

		super.clickOnButton("Auditing records");

		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String course, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String mark) {
		// Este test intenta crear una auditoría con datos incorrectos.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// Este test intenta crear una auditoría usando roles
		// no apropiados.

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();
	}

}
