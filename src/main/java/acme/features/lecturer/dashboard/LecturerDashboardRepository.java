
package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select avg(l.estimatedLearningTime) from Lecture l")
	Double averageLectureLearningTime();

	@Query("select max(l.estimatedLearningTime) from Lecture l")
	Double maxLectureLearningTime();

	@Query("select min(l.estimatedLearningTime) from Lecture l")
	Double minLectureLearningTime();

	@Query("select stddev(l.estimatedLearningTime) from Lecture l")
	Double linDevOfLecturesLearningTime();

}
