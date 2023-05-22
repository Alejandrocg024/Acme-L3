
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumSessionCreateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final int practicumSessionRecordIndex, final String title, final String abstract$, final String startPeriod, final String endPeriod, final String furtherInformationLink) {
		// This test authenticates as a company, list his or her practica, navigates to their practicum sessions, creates a new practicum session and checks that it have the expected data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, title);
		super.checkColumnHasValue(practicumSessionRecordIndex, 1, startPeriod);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, endPeriod);

		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-addendum-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test101Positive(final int practicumRecordIndex, final int practicumSessionRecordIndex, final String title, final String abstract$, final String startPeriod, final String endPeriod, final String furtherInformationLink,
		final String confirmation) {
		// This test authenticates as a company, list his or her practica, navigates to a published practica and into its practicum sessions, creates a new addendum practicum session and checks that it have the expected data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create addendum session");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create addendum session");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, title);
		super.checkColumnHasValue(practicumSessionRecordIndex, 1, startPeriod);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, endPeriod);

		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final String title, final String abstract$, final String startPeriod, final String endPeriod, final String furtherInformationLink) {
		// This test attempts to create practicum sessions using wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-addendum-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int practicumRecordIndex, final int practicumSessionRecordIndex, final String title, final String abstract$, final String startPeriod, final String endPeriod, final String furtherInformationLink,
		final String confirmation) {
		// This test attempts to create an addendum practicum session without pushing confirmation button.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create addendum session");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create addendum session");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// This test tries to create a practicum session for a practicum as a principal without the "Company" role.

		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticaByCompanyUsername("company2");
		for (final Practicum practicum : practica) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// This test tries to create practicum sessions for practica that weren't created by the principal.

		Collection<Practicum> practica;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticaByCompanyUsername("company2");
		for (final Practicum practicum : practica) {
			param = String.format("masterId=%d", practicum.getId());
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
		}
	}

}
