
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCreateTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String furtherInformationLink) {
		// Este test autentica a un auditor, lista sus auditorías, selecciona una
		// para listar sus registros de auditoría y crea uno nuevo,
		// confirmando que se ha creado de manera correcta.
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing records");

		super.clickOnButton("Create");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, assessment);

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String furtherInformationLink) {
		// Este test intenta crear un registro de auditoría con datos incorrectos.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing records");

		super.clickOnButton("Create");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// Este test intenta crear una auditoría usando roles
		// no apropiados.

		Collection<Audit> audits;
		String param;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor2");
		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	public void test301Hacking() {
		// Este test intenta crear un registro de auditoría
		// para una auditoría que no fue creada por el principal

		Collection<Audit> audits;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		audits = this.repository.findManyAuditsByAuditorUsername("auditor2");
		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
		}
	}

}
