
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

	//@Query("select e from Enrolment e where e.student.userAccount.id = :studentId")
	//Collection<Enrolment> findEnrolmentsByStudentId(int studentId);

	//@Query("select e from Enrolment e where e.code = :code")
	//Enrolment findEnrolmentByCode(String code);

	//Activity
	@Query("select a from Activity a where a.id = :activityId")
	Activity findActivityById(int activityId);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findActivitiesByEnrolmentId(int enrolmentId);

	//Course
	//@Query("select c from Course c where c.draftMode = false")
	//Collection<Course> findAllPublishedCourses();

	//@Query("select c from Course c where c.id = :courseId")
	//Course findCourseById(int courseId);

	//Student
	//@Query("select s from Student s where s.id = :studentId")
	//Student findStudentById(int studentId);

}
