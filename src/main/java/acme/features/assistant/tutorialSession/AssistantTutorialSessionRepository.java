
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select ts from TutorialSession ts where ts.id = :id")
	TutorialSession findTutorialSessionById(int id);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findTutorialSessionsByTutorialId(int id);

	@Query("select t from Tutorial t where t.assistant.userAccount.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select ts.tutorial from TutorialSession ts where ts.id = :id")
	Tutorial findTutorialByTutorialSessionId(int id);
}
