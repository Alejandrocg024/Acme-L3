
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialSessionTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorailsByAssistantUsername(String username);

	@Query("select t from TutorialSession t where t.tutorial.assistant.userAccount.username = :username")
	Collection<TutorialSession> findManyTutorailSessionsByAssistantUsername(String username);

	@Query("select t from Tutorial t where t.draftMode=true and t.assistant.userAccount.username = :username")
	Collection<Tutorial> findNonPublishedTutorialsByAssistantUsername(String username);

	@Query("select t from TutorialSession t where t.tutorial.draftMode=true and t.tutorial.assistant.userAccount.username = :username")
	Collection<TutorialSession> findNonPublishedTutorialSessionsByAssistantUsername(String username);

	@Query("select t from Tutorial t where t.draftMode=false and t.assistant.userAccount.username = :username")
	Collection<Tutorial> findPublishedTutorialsByAssistantUsername(String username);

	@Query("select t from TutorialSession t where t.tutorial.draftMode=false and t.tutorial.assistant.userAccount.username = :username")
	Collection<TutorialSession> findPublishedTutorialSessionsByAssistantUsername(String username);
}
