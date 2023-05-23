
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Activity;
import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex) {
		// This test authenticates as a student, lists his or her enrolments, then selects one of them, list its activity and delete one.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.clickOnListingRecord(activityRecordIndex);

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
		// This test tries to delete an activity with a role other than "st".

		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student2");
		for (final Activity activity : activities) {
			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// This test tries to delete an activity that wasn't registered by the principal

		Collection<Activity> activities;
		String params;

		super.signIn("student1", "student1");
		activities = this.repository.findManyActivitiesByStudentUsername("student2");
		for (final Activity activity : activities) {
			params = String.format("id=%d", activity.getId());
			super.request("/student/activity/delete", params);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
