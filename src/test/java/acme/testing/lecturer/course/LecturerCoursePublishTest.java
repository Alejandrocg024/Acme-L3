
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerCoursePublishTest extends TestHarness {

	@Autowired
	LecturerCourseTestRepository repository;


	@Test
	public void test100Positive() {
		//Nos traemos de la base de datos un curso que cumpla las condiciones para ser 
		//publicado a través de su código y lo publicamos
		super.signIn("lecturer1", "lecturer1");
		final Course c = this.repository.findCourseByCode("AIA829");
		final String param = String.format("id=%d", c.getId());
		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(29);
		super.clickOnButton("Lectures");
		super.sortListing(0, "asc");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("draftMode", "false");
		super.checkInputBoxHasValue("nature", "THEORETICAL");
		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(29);
		super.clickOnButton("Lectures");
		super.sortListing(0, "asc");
		super.checkListingExists();
		super.clickOnListingRecord(1);
		super.checkInputBoxHasValue("draftMode", "false");
		super.checkInputBoxHasValue("nature", "HANDS_ON");
		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(29);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		//super.request("/lecturer/course/show", param);
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(29);
		super.checkNotSubmitExists("Publish");
		super.checkNotSubmitExists("Delete");
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();
		super.signOut();
	}

	@Test
	public void test200Negative() {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(28);
		super.clickOnButton("Lectures");
		super.sortListing(0, "asc");
		super.checkListingEmpty();
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(28);
		super.sortListing(0, "asc");
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");
		super.signOut();
	}

	@Test
	public void test201Negative() {
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(27);
		super.sortListing(0, "asc");
		super.clickOnButton("Lectures");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnMenu("Lecturer", "My courses");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(27);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		super.signOut();
	}

	@Test
	public void test202Negative() {

		final String param;
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(34);
		super.sortListing(0, "asc");
		super.clickOnButton("Lectures");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkInputBoxHasValue("nature", "THEORETICAL");
		super.checkInputBoxHasValue("draftMode", "false");
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(34);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos publicar un curso sin ser lo sprofesores de este curso
		final Collection<Course> courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer2");
		for (final Course c : courses) {

			final String param = String.format("id=%d", c.getId());

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		final Collection<Course> courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer2");
		for (final Course c : courses) {

			final String param = String.format("id=%d", c.getId());
			super.signIn("lecturer1", "lecturer1");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test302Hacking() {
		final Collection<Course> courses = this.repository.findPublishedCoursesByLecturerUsername("lecturer1");
		for (final Course c : courses) {

			final String param = String.format("id=%d", c.getId());
			super.signIn("lecturer1", "lecturer1");

			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
