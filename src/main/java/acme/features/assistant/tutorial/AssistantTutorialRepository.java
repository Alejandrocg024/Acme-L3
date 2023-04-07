
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select s from TutorialSession s where s.tutorial = :tutorial")
	Collection<TutorialSession> findTutorialSessionsByTutorial(Tutorial tutorial);

}
