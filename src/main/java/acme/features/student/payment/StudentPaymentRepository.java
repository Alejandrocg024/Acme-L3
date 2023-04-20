
package acme.features.student.payment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentPaymentRepository extends AbstractRepository {

	//Enrolment
	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findEnrolmentById(int id);
}
