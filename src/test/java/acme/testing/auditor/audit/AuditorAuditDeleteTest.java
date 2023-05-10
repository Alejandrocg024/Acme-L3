
package acme.testing.auditor.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditDeleteTest extends TestHarness {

	@Autowired
	AuditorAuditTestRepository repository;


	@Test
	public void test100Positive() {
		//Nos logueamos como auditor1 y borramos todas las auditorias de auditor1 no publicadas
		super.signIn("auditor1", "auditor1");
		final Collection<Audit> audits = this.repository.findNonPublishedAuditsByAuditorUsername("auditor1");
		for (final Audit a : audits) {
			final String param = String.format("id=%d", a.getId());
			super.request("/auditor/audit/show", param);
			super.clickOnSubmit("Delete");
			super.checkNotErrorsExist();
		}
		super.signOut();
	}

	@Test
	public void test200Negative() {
		//Nos logueamos como auditor1 e intentamos borrar sus auditorías publicadas, cosa el sistema no debe permitir
		super.signIn("auditor1", "auditor1");
		final Collection<Audit> audits = this.repository.findNonPublishedAuditsByAuditorUsername("auditor1");
		for (final Audit a : audits) {
			final String param = String.format("id=%d", a.getId());
			super.request("/auditor/audit/show", param);
			super.checkNotButtonExists("Delete");
			super.request("/auditor/audit/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos borrar las auditorías del auditor2, siendo auditor1, administrator o lecturer1
		final Collection<Audit> audits = this.repository.findNonPublishedAuditsByAuditorUsername("auditor2");
		final List<Audit> ls = new ArrayList<>(audits);
		final String param = String.format("id=%d", ls.get(0).getId());//Traemos una auditoría no publicada de otro auditor e intentamos borrarla

		super.signIn("auditor1", "auditor1");
		super.request("/auditor/audit/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/delete", param);
		super.checkPanicExists();
		super.signOut();

	}
}
