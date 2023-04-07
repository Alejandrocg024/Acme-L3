
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(distinct a)  from Audit a inner join Course c on a.course = c where a.auditor = :auditor and c.natureOfCourse = :nature")
	Optional<Integer> findNumOfAuditsByType(Auditor auditor, Nature nature);

	@Query("select avg(ar) from AuditingRecord ar inner join Audit a on ar.audit = a where a.auditor = :auditor")
	Optional<Double> findAverageNumOfAuditingRecords(Auditor auditor);

	@Query("select max(ar) from AuditingRecord ar inner join Audit a on ar.audit = a where a.auditor = :auditor")
	Optional<Double> findMaxNumOfAuditingRecords(Auditor auditor);

	@Query("select min(ar) from AuditingRecord ar inner join Audit a on ar.audit = a where a.auditor = :auditor")
	Optional<Double> findMinNumOfAuditingRecords(Auditor auditor);

	@Query("select stddev(ar) from AuditingRecord ar inner join Audit a on ar.audit = a where a.auditor = :auditor")
	Optional<Double> findLinearDevNumOfAuditingRecords(Auditor auditor);

	@Query("select a from Auditor a where a.userAccount.id = :id")
	Auditor findOneAuditorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select (ar.endPeriod-ar.startPeriod) from AuditingRecord ar inner join Audit a on ar.audit = a where a.auditor = :auditor")
	Collection<Double> findAuditingRecordsPeriodOfTime(Auditor auditor);

}
