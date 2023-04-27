
package acme.testing.lecturer.course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerCourseDeleteTest extends TestHarness {

	@Autowired
	LecturerCourseTestRepository repository;


	@Test
	public void test100Positive() {
		//Nos logueamos como lecturer1 y borramos todos los cursos de lecturer1 no publicados
		//que son los que se pueden borrar
		super.signIn("lecturer1", "lecturer1");
		final Collection<Course> courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer1");
		for (final Course c : courses) {
			final String param = String.format("id=%d", c.getId());
			super.request("/lecturer/course/show", param);
			super.clickOnSubmit("Delete");
			super.checkNotErrorsExist();
		}
		super.signOut();
	}

	@Test
	public void test200Negative() {
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

	@Test
	public void test300Hacking() {
		//Intentamos borrar los cursos del lecturer2, siendo lecturer1, administrator o auditor1
		final Collection<Course> courses = this.repository.findNonPublishedCoursesByLecturerUsername("lecturer2");
		final List<Course> ls = new ArrayList<>(courses);
		final String param = String.format("id=%d", ls.get(0).getId());//Traemos una lección no publicada de otro profesor e intentamos borrarla

		super.signIn("lecturer1", "lecturer1");
		super.request("/lecturer/course/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/delete", param);
		super.checkPanicExists();
		super.signOut();

	}
}
