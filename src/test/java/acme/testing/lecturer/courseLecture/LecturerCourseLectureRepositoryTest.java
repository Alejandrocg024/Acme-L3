
package acme.testing.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseLectureRepositoryTest extends AbstractRepository {

	@Query("select l from Lecture l where l.lecturer.userAccount.username = :username")
	Collection<Lecture> findManyLecturesByLecturerUsername(String username);
}
