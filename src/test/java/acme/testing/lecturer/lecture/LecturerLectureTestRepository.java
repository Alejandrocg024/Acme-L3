
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

public interface LecturerLectureTestRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.lecturer.userAccount.username = :username")
	Collection<Lecture> findManyLecturesByLecturerUsername(String username);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select l from Lecture l where l.draftMode=true and l.lecturer.userAccount.username = :username")
	Collection<Lecture> findNonPublishedLecturesByLecturerUsername(String username);

	@Query("select l from Lecture l where l.draftMode=false and l.lecturer.userAccount.username = :username")
	Collection<Lecture> findPublishedLecturesByLecturerUsername(String username);
}
