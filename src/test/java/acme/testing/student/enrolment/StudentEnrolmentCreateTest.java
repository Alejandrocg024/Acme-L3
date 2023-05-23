
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String course, final String code, final String motivation, final String goals) {
		// HINT: this test authenticates as an student and then lists his or her
		// HINT: enrolment, creates a new one, and check that it's been created properly.

		super.signIn("student5", "student5");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();

		super.clickOnButton("Register");
		super.checkFormExists();
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Register");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, motivation);
		super.checkColumnHasValue(recordIndex, 2, course.substring(course.indexOf("-") + 1));

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String course, final String code, final String motivation, final String goals) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("student5", "student5");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();

		super.clickOnButton("Register");
		super.checkFormExists();
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.clickOnSubmit("Register");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a enrolment using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();
	}

}
