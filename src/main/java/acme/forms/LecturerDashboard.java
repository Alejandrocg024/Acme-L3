
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	/*
	 * 7) The system must handle lecturer dashboards with the following data: total number of theory
	 * and hands-on lectures; average, deviation, minimum, and maximum learning time of the lec-tures;
	 * average, deviation, minimum, and maximum learning time of the courses.
	 */
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
