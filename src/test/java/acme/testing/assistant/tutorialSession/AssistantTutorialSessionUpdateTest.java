
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionUpdateTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial-session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstract$, final String nature, final String startPeriod, final String endPeriod, final String furtherInformationLink) {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(3);
		super.clickOnButton("Tutorial sessions");
		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");
		super.checkNotErrorsExist();
		super.checkListingExists();
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial-session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int recordIndex, final String title, final String abstract$, final String nature, final String startPeriod, final String endPeriod, final String furtherInformationLink) {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(3);
		super.clickOnButton("Tutorial sessions");
		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstract$", abstract$);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("furtherInformationLink", furtherInformationLink);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {

		String param;
		final Collection<TutorialSession> tutorialSessions = this.repository.findManyTutorailSessionsByAssistantUsername("assistant1");
		for (final TutorialSession t : tutorialSessions) {
			param = String.format("id=%d", t.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial-session/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		final Collection<TutorialSession> tutorialSessions;
		String param;
		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		tutorialSessions = this.repository.findManyTutorailSessionsByAssistantUsername("assistant2");
		for (final TutorialSession t : tutorialSessions)
			if (!t.getTutorial().isDraftMode()) {
				param = String.format("id=%d", t.getId());
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
			}
	}
}
