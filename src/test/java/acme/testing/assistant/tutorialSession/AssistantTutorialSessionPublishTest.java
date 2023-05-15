
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialSessionPublishTest extends TestHarness {

	@Autowired
	AssistantTutorialSessionTestRepository repository;


	@Test
	public void test100Positive() {
		final Tutorial t = this.repository.findTutorialByCode("A938");
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Tutorial sessions");
		super.clickOnButton("Create");
		super.fillInputBoxIn("title", "Titulo");
		super.fillInputBoxIn("abstract$", "Resumen");
		super.fillInputBoxIn("startPeriod", "2022/08/01 00:00");
		super.fillInputBoxIn("endPeriod", "2022/08/01 02:00");
		super.fillInputBoxIn("nature", "THEORETICAL");
		super.clickOnSubmit("Create");
		super.checkNotErrorsExist();
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkNotSubmitExists("Publish");
		super.checkNotSubmitExists("Delete");
		super.checkNotSubmitExists("Update");
		super.clickOnButton("Tutorial sessions");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkNotSubmitExists("Delete");
		super.checkNotSubmitExists("Update");
		final String param = String.format("id=%d", t.getId());
		super.request("/assistant/tutorial/publish", param);
		super.checkPanicExists();
		super.request("/assistant/tutorial/delete", param);
		super.checkPanicExists();
		super.request("/assistant/tutorial/update", param);
		super.checkPanicExists();
		super.request("/assistant/tutorial/show", param);

		super.signOut();
	}
	@Test
	public void test200Negative() {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(2);
		super.clickOnButton("Tutorial sessions");
		super.checkListingExists();
		super.checkListingEmpty();
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(2);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");
		super.signOut();
	}

	@Test
	public void test201Negative() {
		final Tutorial t = this.repository.findTutorialByCode("AAA999");

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(5);
		super.clickOnButton("Tutorial sessions");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkNotSubmitExists("Update");
		super.checkNotSubmitExists("Delete");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(5);
		final String param = String.format("id=%d", t.getId());
		super.request("/assistant/tutorial/publish", param);
		super.checkPanicExists();
		super.signOut();
	}

	public void test300Hacking() {

		final Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findNonPublishedTutorialsByAssistantUsername("assistant1");
		for (final Tutorial t : tutorials) {
			param = String.format("id=%d", t.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/publish", param);

			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/tutorial/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/tutorial/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/tutorial/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/assistant/tutorial/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/assistant/tutorial/publish", param);
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
			super.request("/assistant/tutorial/publish", param);
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
			super.request("/assistant/tutorial/publish", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
