
package acme.features.company.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.id = :companyId")
	Company findCompanyById(int companyId);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findPracticaByCompanyId(int companyId);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :practicumId")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int practicumId);

	@Query("select avg(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId")
	Double averageLengthOfPracticumSessionsPerCompany(int companyId);

	@Query("select stddev(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId")
	Double deviationLengthOfPracticumSessionsPerCompany(int companyId);

	@Query("select min(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId")
	Double minimumLengthOfPracticumSessionsPerCompany(int companyId);

	@Query("select max(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId")
	Double maximunLengthOfPracticumSessionsPerCompany(int companyId);

	@Query("select avg(select sum(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId and ps.practicum.id = p.id) from Practicum p")
	Double averageLengthOfPracticumPerCompany(int companyId);

	//	//@Query("select stddev(select sum(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId and ps.practicum.id = p.id) from Practicum p")
	//	@Query("select stddev(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum in (select p from Practicum p where p.company.id = :companyId)")
	Double deviationLengthOfPracticumPerCompany(int companyId);

	@Query("select min(select sum(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId and ps.practicum.id = p.id) from Practicum p")
	Double minimumLengthOfPracticumPerCompany(int companyId);

	@Query("select max(select sum(time_to_sec(timediff(ps.endPeriod, ps.startPeriod))) / 3600 from PracticumSession ps where ps.practicum.company.id = :companyId and ps.practicum.id = p.id) from Practicum p")
	Double maximumLengthOfPracticumPerCompany(int companyId);

}
