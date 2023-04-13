
package acme.features.any.peep;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Lecture;
import acme.entities.Peep;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select l from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.id = :id")
	Collection<Lecture> findLecturesByCourse(int id);

	@Query("select p from Peep p where p.instantiationMoment <= :date")
	Collection<Peep> findAllPeeps(Date date);

	@Query("select p from Peep p where p.id = :id")
	Peep findPeepById(int id);
}
