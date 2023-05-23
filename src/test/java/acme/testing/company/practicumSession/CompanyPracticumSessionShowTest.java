
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final int practicumSessionRecordIndex, final String title, final String abstract$, final String startPeriod, final String endPeriod, final String furtherInformationLink) {
		// This test signs in as a company, lists his or her practica, selects one of them and checks that it shows the correct practicum sessions.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practica");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");
		super.checkListingExists();
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There's no negative test case for this show test because it's not possible to get an error when showing a practicum session.
	}

	@Test
	public void test300Hacking() {
		// This test tries to show a practicum session of a practicum that is not published or not available, but wasn't published by the principal;

		Collection<PracticumSession> practicumSessions;
		String param;

		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company3");
		for (final PracticumSession practicumSession : practicumSessions)
			if (practicumSession.getPracticum().isDraftMode()) {
				param = String.format("id=%d", practicumSession.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
