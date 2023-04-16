
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	//@Query("select count(distinct a) from Audit a inner join Course c on a.course = c where a.auditor = :auditor and c.natureOfCourse = :nature")
	//Optional<Integer> findNumOfAuditsByType(Auditor auditor, Nature nature);

	@Query("select avg(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Optional<Double> findAverageNumOfAuditingRecords(int id);

	@Query("select max(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Optional<Double> findMaxNumOfAuditingRecords(int id);

	@Query("select min(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Optional<Double> findMinNumOfAuditingRecords(int id);

	//@Query("select stddev(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	//Optional<Double> findLinearDevNumOfAuditingRecords(int id);

	@Query("select a from Auditor a where a.userAccount.id = :id")
	Auditor findOneAuditorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select (select ((ar.endPeriod-ar.startPeriod)/3600000) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Collection<Double> findAuditingRecordsPeriodOfTime(int id);

}
