
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionShowTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial-session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstract$, final String nature, final String startPeriod, final String endPeriod, final String furtherInformationLink) {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(5);
		super.clickOnButton("Tutorial sessions");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstract$", abstract$);
		super.checkInputBoxHasValue("nature", nature);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("furtherInformationLink", furtherInformationLink);
		super.signOut();
	}

	@Test
	public void test200Negative() {
		//No hay
	}

	@Test
	public void test300Hacking() {

		String param;
		final Collection<TutorialSession> tutorialSessions = this.repository.findManyTutorailSessionsByAssistantUsername("assistant1");
		for (final TutorialSession t : tutorialSessions) {
			param = String.format("id=%d", t.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial-session/show", param);
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
		for (final TutorialSession t : tutorialSessions) {
			param = String.format("id=%d", t.getId());
			super.request("/assistant/tutorial-session/show", param);
			super.checkPanicExists();
		}
	}
}
