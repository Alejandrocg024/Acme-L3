
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorailsByAssistantUsername(String username);

	@Query("select t from Tutorial t where t.draftMode=true and t.assistant.userAccount.username = :username")
	Collection<Tutorial> findNonPublishedTutorialsByAssistantUsername(String username);

	@Query("select t from Tutorial t where t.draftMode=false and t.assistant.userAccount.username = :username")
	Collection<Tutorial> findPublishedTutorialsByAssistantUsername(String username);

}
