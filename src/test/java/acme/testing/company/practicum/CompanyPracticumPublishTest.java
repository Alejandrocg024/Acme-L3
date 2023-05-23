
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumPublishTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {
		// This test authenticates as a company, lists his or her practica, then selects one of them, and publishes it.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotButtonExists("Publish");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {
		// This test attempts to publish a practicum that cannot be published, yet.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkAlertExists(false);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// This test tries to publish a practicum with a role other than "Company".

		final Practicum p = this.repository.findPracticumByCode("A999");
		final String params = String.format("id=%d", p.getId());

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/publish", params);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum/publish", params);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/company/practicum/publish", params);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/company/practicum/publish", params);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/company/practicum/publish", params);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/company/practicum/publish", params);
		super.checkPanicExists();
		super.signOut();
	}

	@Test
	public void test301Hacking() {
		// This test tries to publish a published practicum that was registered by the principal.

		Collection<Practicum> practica;
		String params;

		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (!practicum.isDraftMode()) {
				params = String.format("id=%d", practicum.getId());
				super.request("/company/practicum/publish", params);
				super.checkPanicExists();
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// This test tries to publish a practicum that wasn't registered by the principal, be it published or unpublished.

		Collection<Practicum> practica;
		String params;

		super.signIn("company2", "company2");
		practica = this.repository.findManyPracticaByCompanyUsername("company1");
		for (final Practicum practicum : practica) {
			params = String.format("id=%d", practicum.getId());
			super.request("/company/practicum/publish", params);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
