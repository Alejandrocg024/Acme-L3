
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.entities.Audit;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.id = :id")
	Collection<Audit> findAudits(int id);

	@Query("select (select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Collection<Double> findNumOfAuditingRecords(int id);

	@Query("select avg(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Optional<Double> findAverageNumOfAuditingRecords(int id);

	@Query("select max(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Optional<Double> findMaxNumOfAuditingRecords(int id);

	@Query("select min(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Optional<Double> findMinNumOfAuditingRecords(int id);

	@Query("select a from Auditor a where a.userAccount.id = :id")
	Auditor findOneAuditorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select avg(datediff(ar.endPeriod, ar.startPeriod)) * 24 from AuditingRecord ar where ar.audit.auditor.id = :id")
	Optional<Double> findAverageDurationOfAuditingRecords(int id);

	@Query("select max(datediff(ar.endPeriod, ar.startPeriod)) * 24 from AuditingRecord ar where ar.audit.auditor.id = :id")
	Optional<Double> findMaxDurationOfAuditingRecords(int id);

	@Query("select min(time_to_sec(timediff(ar.endPeriod, ar.startPeriod)) / 3600) from AuditingRecord ar where ar.audit.auditor.id = :id")
	Optional<Double> findMinDurationOfAuditingRecords(int id);

	@Query("select stddev(datediff(ar.endPeriod, ar.startPeriod)) * 24 from AuditingRecord ar where ar.audit.auditor.id = :id")
	Optional<Double> findLinDevDurationOfAuditingRecords(int id);

	@Query("select count(cl) from CourseLecture cl where cl.course.id = :courseId and cl.lecture.nature = :nature")
	Optional<Integer> findNumLecturesByCourseNature(int courseId, Nature nature);

	default Nature auditNature(final Integer courseId, final int handonLect, final int theoreticalLect) {
		if (theoreticalLect > handonLect)
			return Nature.THEORETICAL;
		else if (theoreticalLect < handonLect)
			return Nature.HANDS_ON;
		else
			return Nature.BALANCED;
	}

	default Map<Nature, Integer> auditsPerNature(final Collection<Audit> audits) {
		final Map<Nature, Integer> res = new HashMap<>();
		for (final Audit a : audits) {
			final int courseId = a.getCourse().getId();
			final int handonLect = this.findNumLecturesByCourseNature(courseId, Nature.HANDS_ON).get();
			final int theoreticalLect = this.findNumLecturesByCourseNature(courseId, Nature.THEORETICAL).get();
			final Nature n = this.auditNature(courseId, handonLect, theoreticalLect);
			if (!res.containsKey(n))
				res.put(n, 1);
			else
				res.put(n, res.get(n) + 1);
		}
		return res;
	}
}
