
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditShowTest extends TestHarness {

	@Autowired
	AuditorAuditTestRepository respository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String course, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String mark) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//No hay
	}

	@Test
	public void test300Hacking() {

		//Intentamos ver las auditorías de un auditor que no somos nosotros
		Collection<Audit> audits;
		String param;

		audits = this.respository.findManyAuditsByAuditorUsername("auditor2");
		for (final Audit a : audits) {
			param = String.format("id=%d", a.getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/audit/show", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
