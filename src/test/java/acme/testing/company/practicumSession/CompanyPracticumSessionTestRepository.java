
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.userAccount.username = :username")
	Collection<Practicum> findManyPracticaByCompanyUsername(String username);

	@Query("select ps from PracticumSession ps where ps.practicum.company.userAccount.username = :username")
	Collection<PracticumSession> findManyPracticumSessionsByCompanyUsername(String username);

}
