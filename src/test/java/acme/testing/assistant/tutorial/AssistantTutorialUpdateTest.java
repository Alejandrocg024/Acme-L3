
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	@Autowired
	AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String goal, final String course) {
		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();

		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", summary);
		super.fillInputBoxIn("goal", goal);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", summary);
		super.checkInputBoxHasValue("goal", goal);
		super.checkInputBoxHasValue("estimatedTotalTime", "0.00");
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}
	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String summary, final String goal, final String course) {
		super.signIn("assistant1", "assistant1");
		Tutorial t;
		t = this.repository.findTutorialByCode("A999");
		final String param = String.format("id=%d", t.getId());
		super.request("/assistant/tutorial/show", param);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", summary);
		super.fillInputBoxIn("goal", goal);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findNonPublishedTutorialsByAssistantUsername("assistant1");
		for (final Tutorial t : tutorials) {
			param = String.format("id=%d", t.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/update", param);

			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findNonPublishedTutorialsByAssistantUsername("assistant1");
		for (final Tutorial t : tutorials) {
			param = String.format("id=%d", t.getId());

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test302Negative() {
		super.signIn("assistant1", "assistant1");
		final Collection<Tutorial> tutorials = this.repository.findPublishedTutorialsByAssistantUsername("assistant1");
		for (final Tutorial t : tutorials) {
			final String param = String.format("id=%d", t.getId());
			super.request("/assistant/tutorial/update", param);
			super.checkPanicExists();
		}
		super.signOut();
	}
}
