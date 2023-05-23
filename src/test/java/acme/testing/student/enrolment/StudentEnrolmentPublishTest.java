
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentPublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected StudentEnrolmentTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String holderName, final String creditCardNumber, final String expirationDate, final String securityCode) {
		// HINT: this test authenticates as an student, lists his or her enrolments,
		// HINT: then selects one of them, and finalise it.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Finalise");
		super.checkFormExists();
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("creditCardNumber", creditCardNumber);
		super.fillInputBoxIn("expirationDate", expirationDate);
		super.fillInputBoxIn("securityCode", securityCode);
		super.clickOnSubmit("Pay the enrolment");

		super.checkFormExists();
		super.checkInputBoxHasValue("holderName", holderName);
		super.checkInputBoxHasValue("lowerNibble", creditCardNumber.substring(creditCardNumber.length() - 4));
		super.checkNotButtonExists("Publish");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String holderName, final String creditCardNumber, final String expirationDate, final String securityCode) {
		// HINT: this test attempts to finalise an enrolment with bad data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Finalise");
		super.checkFormExists();
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("creditCardNumber", creditCardNumber);
		super.fillInputBoxIn("expirationDate", expirationDate);
		super.fillInputBoxIn("securityCode", securityCode);
		super.clickOnSubmit("Pay the enrolment");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to finalise an enrolment for a enrolment that is not finalised 
		// HINT: but is not the principal;

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				param = String.format("enrolmentId=%d", enrolment.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/payment/create", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/payment/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/student/payment/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/student/payment/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/student/payment/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/student/payment/create", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/student/payment/create", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to finalise an activity of a enrolment that is not in draft mode

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
		super.signIn("student2", "student2");
		for (final Enrolment enrolment : enrolments)
			if (!enrolment.isDraftMode()) {
				param = String.format("enrolmentId=%d", enrolment.getId());
				super.request("/student/payment/create", param);
				super.checkPanicExists();
			}
		super.signOut();
	}

}
