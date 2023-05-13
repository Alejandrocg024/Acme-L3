
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AssistantTutorialPublishTest extends TestHarness {

	@Autowired
	AssistantTutorialTestRepository repository;


	@Test
	public void test100Positive() {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(5);
		super.clickOnButton("Tutorial sessions");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
	}
}
