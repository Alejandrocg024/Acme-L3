
package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select avg(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	Double averageLectureLearningTime(Lecturer lecturer);

	@Query("select max(l.estimatedLearningTime) from Lecture l")
	Double maxLectureLearningTime();

	@Query("select min(l.estimatedLearningTime) from Lecture l")
	Double minLectureLearningTime();

	@Query("select stddev(l.estimatedLearningTime) from Lecture l")
	Double linDevOfLecturesLearningTime();

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
