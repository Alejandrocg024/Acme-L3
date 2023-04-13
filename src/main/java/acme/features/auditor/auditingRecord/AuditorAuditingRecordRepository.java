
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select ar.audit from AuditingRecord ar where ar.id = :id")
	Audit findAuditByAuditingRecordId(int id);

	@Query("select ar from AuditingRecord ar where ar.id=:id")
	AuditingRecord findAuditingRecordById(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id=:id")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);

}
