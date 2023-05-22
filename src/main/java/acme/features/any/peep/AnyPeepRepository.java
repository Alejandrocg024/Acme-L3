
package acme.features.any.peep;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	//Peep
	@Query("select p from Peep p where p.instantiationMoment <= :date")
	Collection<Peep> findPeepBeforeDate(Date date);

	@Query("select p from Peep p where p.id = :id")
	Peep findPeepById(int id);

	//User Account
	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);
}
