
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditPublishTest extends TestHarness {

	@Autowired
	AuditorAuditTestRepository repository;


	@Test
	public void test100Positive() {
		//Nos traemos de la base de datos una auditoría que cumpla las condiciones para ser 
		//publicada a través de su código y la publicamos
		super.signIn("auditor1", "auditor1");
		final Audit a = this.repository.findAuditByCode("A999");
		final String param = String.format("id=%d", a.getId());
		super.request("/auditor/audit/show", param);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		super.request("/auditor/audit/show", param);
		super.checkNotSubmitExists("Publish");
		super.signOut();
	}

	@Test
	public void test200Negative() {
		//Con el procedimiento anterior intetamos publicar auditorías que no se pueden publicar por las siguientes razones:
		//No tienen registros de auditorías
		//Todos sus registros de auditorías no están publicados
		String param;
		super.signIn("auditor1", "auditor1");
		final Audit auditNotAR = this.repository.findAuditByCode("A900");
		param = String.format("id=%d", auditNotAR.getId());
		super.request("/auditor/audit/show", param);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		final Audit auditARNotPubl = this.repository.findAuditByCode("AA999");
		param = String.format("id=%d", auditARNotPubl.getId());
		super.request("/auditor/audit/show", param);
		super.clickOnSubmit("Publish");
		super.checkErrorsExist("*");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos publicar una auditoría sin ser los creadores de dicha auditoría

		final Audit a = this.repository.findAuditByCode("AAA999");
		final String param = String.format("id=%d", a.getId());

		super.signIn("auditor2", "auditor2");
		super.request("/auditor/audit/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/publish", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/publish", param);
		super.checkPanicExists();
		super.signOut();

	}

}
