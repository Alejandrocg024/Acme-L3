
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final String code, final int activityRecordIndex, final String title, final String startPeriod, final String endPeriod) {
		// HINT: this test authenticates as an student, then lists his or her enrolments, 
		// HINT+ selects one of them, and check that it has the expected activities.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(enrolmentRecordIndex, 0, code);
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnButton("Workbook");

		super.checkListingExists();
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, startPeriod);
		super.checkColumnHasValue(activityRecordIndex, 2, endPeriod);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list the activities of a enrolment that is finalised
		// HINT+ using a principal that didn't create it. 

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (!enrolment.isDraftMode()) {
				param = String.format("enrolmentId=%d", enrolment.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/activity/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student2", "student2");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/student/enrolment/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/student/enrolment/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to show an activity of a enrolment that is in not finalised

		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student2");
		super.signIn("student2", "student2");
		for (final Activity activity : activities)
			if (activity.getEnrolment().isDraftMode()) {
				param = String.format("enrolmentId=%d", activity.getId());
				super.request("/student/activity/list", param);
				super.checkPanicExists();
			}
		super.signOut();
	}

}
