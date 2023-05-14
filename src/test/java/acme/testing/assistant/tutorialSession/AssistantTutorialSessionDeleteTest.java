
package acme.testing.assistant.tutorialSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@Test
	public void test100Positive() {
		super.signIn("assistant1", "assistant1");
		final Collection<TutorialSession> tutorialSession = this.repository.findNonPublishedTutorialSessionsByAssistantUsername("assistant1");
		for (final TutorialSession t : tutorialSession) {
			final String param = String.format("id=%d", t.getId());
			super.request("/assistant/tutorial-session/show", param);
			super.clickOnSubmit("Delete");
			super.checkNotErrorsExist();
		}
		super.signOut();
	}

	@Test
	public void test200Negative() {
		super.signIn("assistant1", "assistant1");
		final Collection<TutorialSession> tutorialSession = this.repository.findPublishedTutorialSessionsByAssistantUsername("assistant1");
		for (final TutorialSession t : tutorialSession) {
			final String param = String.format("id=%d", t.getId());
			super.request("/assistant/tutorial-session/show", param);
			super.checkNotSubmitExists("Delete");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkErrorsExist();
		}
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		String param;
		final Collection<TutorialSession> tutorialSessions = this.repository.findNonPublishedTutorialSessionsByAssistantUsername("assistant2");
		for (final TutorialSession t : tutorialSessions) {
			param = String.format("id=%d", t.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

	@Test
	public void test301Hacking() {
		final Collection<TutorialSession> tutorialSessions = this.repository.findPublishedTutorialSessionsByAssistantUsername("assistant1");
		final List<TutorialSession> ls = new ArrayList<>(tutorialSessions);
		final String param = String.format("id=%d", ls.get(0).getId());

		super.signIn("assistant2", "assistant2");
		super.request("/assistant/tutorial-session/delete", param);
		super.checkPanicExists();
		super.signOut();

	}

}
