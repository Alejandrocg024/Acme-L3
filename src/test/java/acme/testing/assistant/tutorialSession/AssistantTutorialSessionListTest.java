
package acme.testing.assistant.tutorialSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialSessionListTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial-session/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String startDate, final String abstract$) {
		super.signIn("assistant1", "assistant1");
		final Tutorial t = this.repository.findTutorialByCode("AAA999");
		final String param = String.format("masterId=%d", t.getId());
		super.request("/assistant/tutorial-session/list", param);
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startDate);
		super.checkColumnHasValue(recordIndex, 2, abstract$);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//no existen casos de prueba negativos
	}

	@Test
	public void test300Hacking() {
		final Tutorial t = this.repository.findTutorialByCode("AAA999");
		final String param = String.format("masterId=%d", t.getId());
		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial-session/list", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/tutorial-session/list", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial-session/list", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/assistant/tutorial-session/list", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/tutorial-session/list", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/tutorial-session/list", param);
		super.checkPanicExists();
		super.signOut();
	}

}
