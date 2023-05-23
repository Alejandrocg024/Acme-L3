
package acme.testing.lecturer.courseLecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerCourseLectureCreateTest extends TestHarness {

	@Autowired
	LecturerCourseLectureRepositoryTest respository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String ETL) {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Add lecture to a course");
		super.checkFormExists();
		super.fillInputBoxIn("course", code);
		super.clickOnSubmit("Add");
		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Lectures");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, summary);
		super.checkColumnHasValue(recordIndex, 2, ETL);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String summary, final String ETL) {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Add lecture to a course");
		super.checkFormExists();
		super.fillInputBoxIn("course", code);
		super.clickOnSubmit("Add");
		super.checkErrorsExist();
	}

	@Test
	public void test300Hacking() {
		final Collection<Lecture> ls = this.respository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture l : ls) {
			final String param = String.format("lectureId=%d", l.getId());
			super.checkLinkExists("Sign in");
			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {
		final Collection<Lecture> ls = this.respository.findManyLecturesByLecturerUsername("lecturer1");
		super.signIn("lecturer2", "lecturer2");
		for (final Lecture l : ls) {
			final String param = String.format("lectureId=%d", l.getId());

			super.request("/lecturer/course-lecture/create", param);
			super.checkPanicExists();

		}
		super.signOut();

	}

}
