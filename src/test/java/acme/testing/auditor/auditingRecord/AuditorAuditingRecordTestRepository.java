
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditingRecordTestRepository extends AbstractRepository {

	@Query("select ar from AuditingRecord ar where ar.draftMode=true and ar.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecord> findNonPublishedAuditingRecordsByAuditorUsername(String username);

	@Query("select ar from AuditingRecord ar where ar.draftMode=false and ar.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecord> findPublishedAuditingRecordsByAuditorUsername(String username);

	@Query("select a from Audit a where a.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select ar from AuditingRecord ar where ar.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditorUsername(String username);

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	Collection<Audit> findManyAuditsByAuditorUsername(String username);

}
