
package acme.testing.lecturer.course;

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
		super.request("/lecturer/course/show", param);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		super.request("/lecturer/course/show", param);
		super.checkNotSubmitExists("Publish");
		super.signOut();
	}

	@Test
	public void test200Negative() {
		//Con el procedimiento anterior intetamos publicar cursos que no se pueden publicar por las siguientes razones:
		//No tienen lecciones
		//No tienen lecciones practicas
		//Las lecciones de ese curso no están publicadas
		//El curso ya está publicado
		String param;
		super.signIn("lecturer1", "lecturer1");
		final Course cursoSL = this.repository.findCourseByCode("AAA721");
		param = String.format("id=%d", cursoSL.getId());
		super.request("/lecturer/course/show", param);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		final Course cursoLNP = this.repository.findCourseByCode("YIY781");
		param = String.format("id=%d", cursoLNP.getId());
		super.request("/lecturer/course/show", param);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		final Course cursoNHL = this.repository.findCourseByCode("AAA989");
		param = String.format("id=%d", cursoNHL.getId());
		super.request("/lecturer/course/show", param);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		final Course cursoYP = this.repository.findCourseByCode("AAA999");
		param = String.format("id=%d", cursoYP.getId());
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos publicar un curso sin ser lo sprofesores de este curso

		final Course c = this.repository.findCourseByCode("AIA829");
		final String param = String.format("id=%d", c.getId());

		super.signIn("lecturer2", "lecturer2");
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();
		super.signOut();

	}

}
