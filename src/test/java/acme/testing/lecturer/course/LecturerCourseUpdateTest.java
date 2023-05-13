
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerCourseUpdateTest extends TestHarness {

	@Autowired
	LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstract$, final String price, final String furtherInformationLink, final String code) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("price", price);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);

		super.signOut();

	}
	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abstract$, final String price, final String furtherInformationLink) {
		super.signIn("lecturer1", "lecturer1");
		final Course c;
		c = this.repository.findCourseByCode("AAA997");
		final String param = String.format("id=%d", c.getId());
		super.request("/lecturer/course/show", param);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();

	}

	@Test
	public void test300Hacking() {

		final Collection<Course> courses;
		String param;

		courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer1");
		for (final Course c : courses) {
			param = String.format("id=%d", c.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {

		final Collection<Course> courses;
		String param;

		courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer1");
		for (final Course c : courses) {
			param = String.format("id=%d", c.getId());

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test302Negative() {
		//Nos logueamos como lecturer1 e intentamos borrar sus cursos publicados, cosa el sistema no debe permitir
		super.signIn("lecturer1", "lecturer1");
		final Collection<Course> courses = this.repository.findPublishedCoursesByLecturerUsername("lecturer1");
		for (final Course c : courses) {
			final String param = String.format("id=%d", c.getId());
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
		}
		super.signOut();
	}
}
