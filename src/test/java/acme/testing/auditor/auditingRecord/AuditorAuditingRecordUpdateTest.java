
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String furtherInformationLink) {
		// Este test autentica a un auditor, lista sus auditorías, escoge un registro
		// y lo actualiza, confirmando que se ha actualizado de manera correcta.

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing records");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, assessment);

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String furtherInformationLink) {
		// Este test intenta actualizar un registro de auditoría con datos incorrectos.

		super.signIn("auditor2", "auditor2");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing records");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// Este test intenta actualizar un registro de auditoría usando roles
		// no apropiados.

		Collection<AuditingRecord> auditingRecords;
		String param;

		auditingRecords = this.repository.findManyAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord ar : auditingRecords) {
			param = String.format("id=%d", ar.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// Este test intenta actualizar un registro de auditoría que ya
		// ha sido publicado.

		Collection<AuditingRecord> auditingRecords;
		String param;
		super.checkLinkExists("Sign in");
		super.signIn("auditor2", "auditor2");
		auditingRecords = this.repository.findManyAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord ar : auditingRecords)
			if (!ar.isDraftMode()) {
				param = String.format("masterId=%d", ar.getId());
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
			}
	}

}
