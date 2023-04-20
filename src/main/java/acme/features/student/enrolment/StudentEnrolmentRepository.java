
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	//Enrolment
	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("select e from Enrolment e where e.student.userAccount.id = :studentId")
	Collection<Enrolment> findEnrolmentsByStudentId(int studentId);

	@Query("select e from Enrolment e where e.code = :code")
	Enrolment findEnrolmentByCode(String code);

	//Activity
	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findActivitiesByEnrolmentId(int id);

	//Course
	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findAllPublishedCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	//Student
	@Query("select s from Student s where s.id = :id")
	Student findStudentById(int id);
}
