
package acme.testing.lecturer.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLecturePublishTest extends TestHarness {

	@Autowired
	LecturerLectureTestRepository repository;


	@Test
	public void test100Positive() {
		//Nos logueamos como lecturer1 y publicamos todas las lecciones de lecturer1 no publicadas
		//que son las que se pueden publicar
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(27);
		super.checkFormExists();
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		super.checkNotSubmitExists("Publish");
		super.clickOnMenu("Lecturer", "My lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(27);
		super.checkInputBoxHasValue("draftMode", "false");
		super.signOut();

	}

	@Test
	public void test200Negative() {
		//No tenemos caso negativo

	}

	@Test
	public void test300Hacking() {
		//Intentamos publicar las lecciones del lecturer2, siendo lecturer1, administrator o auditor1
		final Collection<Lecture> lectures = this.repository.findNonPublishedLecturesByLecturerUsername("lecturer2");
		final List<Lecture> ls = new ArrayList<>(lectures);
		final String param = String.format("id=%d", ls.get(0).getId());//Traemos una lección no publicada de otro profesor e intentamos borrarla

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();
		super.signOut();

	}

	@Test
	public void test301Hacking() {
		//Nos logueamos como lecturer1 e intentamos publicar sus lecciones publicadas, cosa el sistema no debe permitir
		super.signIn("lecturer1", "lecturer1");
		final Collection<Lecture> lectures = this.repository.findPublishedLecturesByLecturerUsername("lecturer1");
		for (final Lecture l : lectures) {
			final String param = String.format("id=%d", l.getId());
			super.request("/lecturer/lecture/show", param);
			super.checkNotButtonExists("Publish");
			super.request("/lecturer/lecture/publish", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		//Intentamos publicar las lecciones del lecturer2, siendo lecturer1
		final Collection<Lecture> lectures = this.repository.findNonPublishedLecturesByLecturerUsername("lecturer2");
		final List<Lecture> ls = new ArrayList<>(lectures);
		final String param = String.format("id=%d", ls.get(0).getId());//Traemos una lección no publicada de otro profesor e intentamos borrarla

		super.signIn("lecturer1", "lecturer1");
		super.request("/lecturer/lecture/publish", param);
		super.checkPanicExists();
		super.signOut();

	}
}
