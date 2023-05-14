
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	@Autowired
	AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkListingExists();
		super.signOut();
	}

	public void test200Negative() {
		//No hay pruebas negativas en el borrado, el caso de intentar borrar una tutoria publicada lo probamos en el test302Hacking
	}

	@Test
	public void test300Hacking() {
		final Collection<Tutorial> tutorials = this.repository.findNonPublishedTutorialsByAssistantUsername("assistant2");
		for (final Tutorial t : tutorials) {

			final String param = String.format("id=%d", t.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

	@Test
	public void test301Hacking() {
		final Collection<Tutorial> tutorials = this.repository.findNonPublishedTutorialsByAssistantUsername("assistant2");
		for (final Tutorial t : tutorials) {

			final String param = String.format("id=%d", t.getId());

			super.signIn("assistant1", "assistant1");
			super.request("/assistant/tutorial/delete", param);
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
			super.request("/assistant/tutorial/show", param);
			super.checkNotButtonExists("Delete");
			super.request("/assistant/tutorial/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}
}
