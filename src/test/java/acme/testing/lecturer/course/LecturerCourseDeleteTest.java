
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerCourseDeleteTest extends TestHarness {

	@Autowired
	LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex) {
		//Nos logueamos como lecturer1 y borramos todos los cursos de lecturer1 no publicados
		//que son los que se pueden borrar
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkListingExists();
		super.signOut();
	}

	public void test200Negative() {
		//No hay pruebas negativas en el borrado, el caso de intentar borrar un curso publicado lo probamos en el test302Hacking
	}

	@Test
	public void test300Hacking() {
		//Intentamos borrar los cursos del lecturer2, siendo lecturer1, administrator o auditor1
		final Collection<Course> courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer2");
		for (final Course c : courses) {

			final String param = String.format("id=%d", c.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

	@Test
	public void test301Hacking() {
		//Intentamos borrar los cursos del lecturer2, siendo lecturer1
		final Collection<Course> courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer2");
		for (final Course c : courses) {

			final String param = String.format("id=%d", c.getId());

			super.signIn("lecturer1", "lecturer1");
			super.request("/lecturer/course/delete", param);
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
			super.request("/lecturer/course/show", param);
			super.checkNotButtonExists("Delete");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}
}
