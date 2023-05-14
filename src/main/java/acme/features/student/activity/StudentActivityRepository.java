
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	//Enrolment
	@Query("select e from Enrolment e where e.id = :enrolmentId")
	Enrolment findEnrolmentById(int enrolmentId);

	@Query("select a.enrolment from Activity a where a.id = :activityId")
	Enrolment findEnrolmentByActivityId(int activityId);

	//Activity
	@Query("select a from Activity a where a.id = :activityId")
	Activity findActivityById(int activityId);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findActivitiesByEnrolmentId(int enrolmentId);

}
