
package acme.features.assistant.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	default Nature tutorialNature(final Integer courseId, final int handsOnLecture, final int theoreticalLecture) {
		if (theoreticalLecture > handsOnLecture)
			return Nature.THEORETICAL;
		else if (theoreticalLecture < handsOnLecture)
			return Nature.HANDS_ON;
		else
			return Nature.BALANCED;
	}

	default Map<Nature, Integer> tutorialPerNature(final Collection<Tutorial> tutorials) {
		final Map<Nature, Integer> acum = new HashMap<>();
		for (final Tutorial t : tutorials) {
			final int course = t.getCourse().getId();
			final int handsOn = this.countLecturesByCourseAndNature(course, Nature.HANDS_ON);
			final int theoretical = this.countLecturesByCourseAndNature(course, Nature.THEORETICAL);
			final Nature nature = this.tutorialNature(course, handsOn, theoretical);
			if (!acum.containsKey(nature))
				acum.put(nature, 1);
			else
				acum.put(nature, acum.get(nature) + 1);
		}
		return acum;
	}

	@Query("select count(cl) from CourseLecture cl where cl.course.id = :course and cl.lecture.nature = :nature")
	Integer countLecturesByCourseAndNature(int course, Nature nature);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select a from Assistant a where a.userAccount.id = :id")
	Assistant findAssitantByUserAccountId(int id);

	@Query("select t from Tutorial t where t.assistant = :assistant")
	Collection<Tutorial> findTutorialsByAssistant(Assistant assistant);

	@Query("select t from TutorialSession t where t.tutorial = :tutorial")
	Collection<TutorialSession> findTutorialSessionsByTutorial(Tutorial tutorial);

	@Query("select t from TutorialSession t where t.tutorial.assistant = :assistant")
	Collection<TutorialSession> findTutorialSessionsByAssistant(Assistant assistant);

}
