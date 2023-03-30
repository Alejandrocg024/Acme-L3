
package acme.components;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuxiliarRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
