
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String course, final String code, final String conclusion, final String strongPoints, final String weakPoints) {
		// Este test autentica a un auditor, lista sus auditorías y actualiza una existente,
		// confirmando que se ha actualizado de manera correcta.

		super.signIn("auditor1", "auditor1");

		final Collection<Audit> audits = this.repository.findNonPublishedAuditsByAuditorUsername("auditor1");
		final Audit a = audits.stream().findFirst().get();
		final String param1 = String.format("id=%d", a.getId());
		super.request("/auditor/audit/show", param1);

		super.checkFormExists();
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.clickOnSubmit("Update");

		final Audit a2;
		a2 = this.repository.findAuditByCode(code);
		final String param2 = String.format("id=%d", a2.getId());
		super.request("/auditor/audit/show", param2);

		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String course, final String code, final String conclusion, final String strongPoints, final String weakPoints) {
		// Este test intenta actualizar una auditoría con datos incorrectos.

		super.signIn("auditor1", "auditor1");

		//Añadir el csv
		final Collection<Audit> audits = this.repository.findNonPublishedAuditsByAuditorUsername("auditor1");
		final Audit a = audits.stream().findFirst().get();
		final String param = String.format("id=%d", a.getId());
		super.request("/auditor/audit/show", param);
		super.checkFormExists();
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// Este test intenta actualizar una auditoría usando roles
		// no apropiados.

		Collection<Audit> audits;
		String param;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");
		for (final Audit a : audits) {
			param = String.format("id=%d", a.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
