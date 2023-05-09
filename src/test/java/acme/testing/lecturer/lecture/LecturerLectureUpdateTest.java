
package acme.testing.lecturer.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureUpdateTest extends TestHarness {

	@Autowired
	LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String estimatedLearningTime, final String body, final String nature, final String furtherInformationLink) {
		super.signIn("lecturer1", "lecturer1");

		final Collection<Lecture> lectures = this.repository.findNonPublishedLecturesByLecturerUsername("lecturer1");
		for (final Lecture l : lectures) {
			final String param = String.format("id=%d", l.getId());
			super.request("/lecturer/lecture/show", param);
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("summary", summary);
			super.fillInputBoxIn("estimatedLearningTime", estimatedLearningTime);
			super.fillInputBoxIn("body", body);
			super.fillInputBoxIn("nature", nature);
			super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
			super.clickOnSubmit("Update");
			super.request("/lecturer/lecture/show", param);
			super.checkInputBoxHasValue("title", title);
			super.checkInputBoxHasValue("summary", summary);
			super.checkInputBoxHasValue("estimatedLearningTime", estimatedLearningTime);
			super.checkInputBoxHasValue("body", body);
			super.checkInputBoxHasValue("nature", nature);
			super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);
		}

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String estimatedLearningTime, final String body, final String nature, final String furtherInformationLink) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My lectures");
		final Collection<Lecture> lectures = this.repository.findNonPublishedLecturesByLecturerUsername("lecturer1");
		final List<Lecture> ls = new ArrayList<>(lectures);
		final Lecture l = ls.get(0);
		final String param = String.format("id=%d", l.getId());
		super.request("/lecturer/lecture/show", param);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("estimatedLearningTime", estimatedLearningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		final Collection<Lecture> lectures = this.repository.findNonPublishedLecturesByLecturerUsername("lecturer1");
		for (final Lecture l : lectures) {
			final String param = String.format("id=%d", l.getId());
			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
