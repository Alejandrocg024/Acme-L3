
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordShowTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository respository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditing-record/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String furtherInformationLink) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing records");
		super.checkListingExists();
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

	@Test
	public void test200Negative() {
		//No hay
	}

	@Test
	public void test300Hacking() {

		//Intentamos ver los registros de auditoría de un auditor que no somos nosotros
		Collection<AuditingRecord> auditingRecords;
		String param;

		auditingRecords = this.respository.findManyAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord ar : auditingRecords) {
			param = String.format("id=%d", ar.getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
