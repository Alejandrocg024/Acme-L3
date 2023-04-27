
package acme.testing.auditor.auditingRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordDeleteTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;


	@Test
	public void test100Positive() {
		//Nos logueamos como auditor1 y borramos todos los registros de auditorias de auditor1 no publicados
		super.signIn("auditor1", "auditor1");
		final Collection<AuditingRecord> auditingRecords = this.repository.findNonPublishedAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord ar : auditingRecords) {
			final String param = String.format("id=%d", ar.getId());
			super.request("/auditor/auditing-record/show", param);
			super.clickOnSubmit("Delete");
			super.checkNotErrorsExist();
		}
		super.signOut();
	}

	@Test
	public void test200Negative() {
		//Nos logueamos como auditor1 e intentamos borrar sus registros de auditorías publicadas, el sistema no debe permitirlo
		super.signIn("auditor1", "auditor1");
		final Collection<AuditingRecord> auditingRecords = this.repository.findPublishedAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord ar : auditingRecords) {
			final String param = String.format("id=%d", ar.getId());
			super.request("/auditor/auditing-record/show", param);
			super.checkNotButtonExists("Delete");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		//Intentamos borrar los registros de auditoría del auditor1, siendo auditor2, administrator o lecturer1
		final Collection<AuditingRecord> auditingRecords = this.repository.findNonPublishedAuditingRecordsByAuditorUsername("auditor2");
		final List<AuditingRecord> ls = new ArrayList<>(auditingRecords);
		final String param = String.format("id=%d", ls.get(0).getId());//Traemos un registro de auditoría no publicado de otro auditor e intentamos borrarlo

		super.signIn("auditor1", "auditor1");
		super.request("/auditor/auditing-record/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("administrator", "administrator");
		super.request("/auditor/auditing-record/delete", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/auditing-record/delete", param);
		super.checkPanicExists();
		super.signOut();

	}
}
