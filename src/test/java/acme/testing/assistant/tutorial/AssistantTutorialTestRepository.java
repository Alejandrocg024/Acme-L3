
package acme.testing.assistant.tutorial;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);

}
