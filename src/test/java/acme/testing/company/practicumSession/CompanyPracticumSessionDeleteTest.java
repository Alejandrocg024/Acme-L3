
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final int practicumSessionRecordIndex) {
		// This test authenticates as a company, lists his or her practica, then selects one of them, list its practicum sessions and deletes it.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.clickOnListingRecord(practicumSessionRecordIndex);

		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There aren't any negative tests for this feature because it's not possible to get an error when pushing "Delete" submit button.
	}

	@Test
	public void test300Hacking() {
		// This test tries to delete a practicum session with a role other than "Company".

		Collection<PracticumSession> practicumSessions;
		String param;

		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company3");
		for (final PracticumSession practicumSession : practicumSessions) {
			param = String.format("id=%d", practicumSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// This test tries to delete a practicum session of a published practicum that was registered by the principal.

		Collection<PracticumSession> practicumSessions;
		String params;

		super.signIn("company4", "company4");
		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company4");
		for (final PracticumSession practicumSession : practicumSessions)
			if (!practicumSession.getPracticum().isDraftMode()) {
				params = String.format("id=%d", practicumSession.getId());
				super.request("/company/practicum-session/delete", params);
				super.checkPanicExists();
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// This test tries to delete a practicum session that wasn't registered by the principal, be it published or unpublished.

		Collection<PracticumSession> practicumSessions;
		String params;

		super.signIn("company2", "company2");
		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions) {
			params = String.format("id=%d", practicumSession.getId());
			super.request("/company/practicum-session/delete", params);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
