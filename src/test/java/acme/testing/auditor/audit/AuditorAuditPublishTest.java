
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditPublishTest extends TestHarness {

	@Autowired
	AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {
		//Nos traemos de la base de datos una auditoría que cumpla las condiciones para ser 
		//publicada y la publicamos
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkNotSubmitExists("Publish");
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/publish-negative1.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {
		//Con el procedimiento anterior intentamos publicar auditorías que no se pueden publicar por las siguientes razones:
		//No tienen registros de auditorías
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/publish-negative2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String code) {
		//Con el procedimiento anterior intentamos publicar auditorías que no se pueden publicar por las siguientes razones:
		//Todos sus registros de auditorías no están publicados
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos publicar una auditoría sin ser los creadores de dicha auditoría

		final Audit a = this.repository.findAuditByCode("A903");
		final String param = String.format("id=%d", a.getId());

		super.signIn("auditor2", "auditor2");
		super.request("/auditor/audit/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/publish", param);
		super.checkPanicExists();
		super.signOut();

	}

	@Test
	public void test301Hacking() {
		//Intentamos publicar auditorías que no se pueden publicar
		//debido a que ya lo están
		final Audit a = this.repository.findAuditByCode("AAA999");
		final String param = String.format("id=%d", a.getId());

		super.signIn("auditor1", "auditor1");
		super.request("/auditor/audit/publish", param);

		super.checkPanicExists();
		super.signOut();

	}

}
