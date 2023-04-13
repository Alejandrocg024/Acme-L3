
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.course.id = :masterId and t.draftMode = false")
	Collection<Tutorial> findTutorialByCourseId(int masterId);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select s from TutorialSession s where s.tutorial = :tutorial")
	Collection<TutorialSession> findTutorialSessionsByTutorial(Tutorial tutorial);

}
