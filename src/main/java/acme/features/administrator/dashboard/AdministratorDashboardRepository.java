
package acme.features.administrator.dashboard;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Note;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(u) from Lecturer l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByLecturer();

	@Query("select count(u) from Assistant l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByAssistant();

	@Query("select count(u) from Auditor l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByAuditor();

	@Query("select count(u) from Company l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByCompany();

	@Query("select count(u) from Consumer l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByConsumer();

	@Query("select count(u) from Provider l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByProvider();

	@Query("select count(u) from Student l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByStudent();

	@Query("select count(u) from Administrator l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByAdministrator();

	@Query("select count(l) from Peep l ")
	Double countAllPeeps();

	@Query("select count(l) from Peep l where l.email is not null and l.link is not null")
	Double countAllPeepsWithBoth();

	@Query("select count(l) from Bulletin l ")
	Double countAllBulletin();

	@Query("select count(l) from Bulletin l where l.critical = true")
	Double countAllCriticalBulletin();

	@Query("select count(l) from Bulletin l where l.critical = false")
	Double countAllNonCriticalBulletin();

	@Query("select l.price.amount from Offer l where l.price.currency = :currency")
	Collection<Double> findPriceOfferByUSD(String currency);

	@Query("select n from Note n where n.instantiationMoment >= :date ")
	Collection<Note> findNotesInLast10Weeks(Date date);

}
