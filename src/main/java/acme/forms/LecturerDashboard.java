
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						theoryLectures;
	Integer						handsOnLectures;
	Double						averageLearningTimeOfLectures;
	Double						deviationOfLearningTimeOfLectures;
	Double						minimumLearningTimeOfLectures;
	Double						maximumLearningTimeOfLectures;
	Double						averageLearningTimeOfCourses;
	Double						deviationOfLearningTimeOfCourses;
	Double						minimumLearningTimeOfCourses;
	Double						maximumLearningTimeOfCourses;

}
