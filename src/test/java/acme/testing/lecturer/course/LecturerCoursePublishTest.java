
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
		//super.request("/lecturer/course/show", param);
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(29);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		//super.request("/lecturer/course/show", param);
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(29);
		super.checkNotSubmitExists("Publish");
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();
		super.signOut();
	}

	@Test
	public void test200Negative() {

		final String param;
		super.signIn("lecturer1", "lecturer1");
		//final Course cursoSL = this.repository.findCourseByCode("AAA721");
		//param = String.format("id=%d", cursoSL.getId());
		//super.request("/lecturer/course/show", param);
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(28);
		super.clickOnButton("Lectures");
		super.checkListingEmpty();
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(28);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		super.signOut();
	}

	@Test
	public void test201Negative() {
		final String param;
		super.signIn("lecturer1", "lecturer1");
		//final Course cursoLNP = this.repository.findCourseByCode("YIY781");
		//param = String.format("id=%d", cursoLNP.getId());
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(27);
		//super.request("/lecturer/course/show", param);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		super.signOut();
	}

	@Test
	public void test202Negative() {

		final String param;
		super.signIn("lecturer1", "lecturer1");

		final Course cursoNHL = this.repository.findCourseByCode("AAA989");
		//param = String.format("id=%d", cursoNHL.getId());
		//super.request("/lecturer/course/show", param);
		super.clickOnMenu("Lecturer", "My courses");
		super.clickOnListingRecord(30);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos publicar un curso sin ser lo sprofesores de este curso

		final Course c = this.repository.findCourseByCode("AIA829");
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

	@Test
	public void test301Hacking() {
		//Intentamos publicar un curso sin ser lo sprofesores de este curso

		final Course c = this.repository.findCourseByCode("AIA829");
		final String param = String.format("id=%d", c.getId());

		super.signIn("lecturer2", "lecturer2");
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();
		super.signOut();

	}

	@Test
	public void test302Hacking() {
		String param;
		super.signIn("lecturer1", "lecturer1");
		final Course cursoYP = this.repository.findCourseByCode("AAA999");
		param = String.format("id=%d", cursoYP.getId());
		super.request("/lecturer/course/publish", param);
		super.checkPanicExists();

		super.signOut();
	}

}
