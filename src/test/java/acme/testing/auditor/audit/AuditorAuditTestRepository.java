
package acme.testing.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Audit;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.draftMode=true and a.auditor.userAccount.username = :username")
	Collection<Audit> findNonPublishedAuditsByAuditorUsername(String username);

	@Query("select a from Audit a where a.code=:code")
	Audit findAuditByCode(String code);

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	Collection<Audit> findManyAuditsByAuditorUsername(String username);

}
