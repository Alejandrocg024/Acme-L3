
package acme.components;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.CurrencyCache;
import acme.entities.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuxiliarRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select cc from CurrencyCache cc where cc.origenCurrency = :orgCurrency and cc.destinationCurrency = :dstCurrency")
	CurrencyCache getCurrencyCacheByChange(String orgCurrency, String dstCurrency);
}
