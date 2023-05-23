
package acme.features.lecturer.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.forms.LecturerDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final LecturerDashboard dashboard = new LecturerDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Lecturer lecturer = this.repository.findOneLecturerByUserAccountId(userAccountId);
		//lecturesStats
		final double averageLectureLearningTime = this.repository.findAverageLectureLearningTime(lecturer).orElse(0.0);
		final double maxLectureLearningTime = this.repository.findMaxLectureLearningTime(lecturer).orElse(0.0);
		final double minLectureLearningTime = this.repository.findMinLectureLearningTime(lecturer).orElse(0.0);
		final double devLectureLearningTime = this.repository.findLinearDevLectureLearningTime(lecturer).orElse(0.0);
		final Statistic lectureStats = new Statistic();
		lectureStats.setAverage(averageLectureLearningTime);
		lectureStats.setMin(minLectureLearningTime);
		lectureStats.setMax(maxLectureLearningTime);
		lectureStats.setLinDev(devLectureLearningTime);
		dashboard.setLecturesStats(lectureStats);

		//coursesStats
		//final double averageCourseLearningTime = this.repository.findAverageCourseLearningTime(lecturer);
		final Statistic coursesStats = new Statistic();
		final Collection<Double> courseEstimatedLearningTime = this.repository.findEstimatedLearningTimeByCourse(lecturer);
		coursesStats.calcAverage(courseEstimatedLearningTime);
		coursesStats.calcMax(courseEstimatedLearningTime);
		coursesStats.calcMin(courseEstimatedLearningTime);
		coursesStats.calcLinDev(courseEstimatedLearningTime);
		dashboard.setCoursesStats(coursesStats);

		//numOfLecturesByType
		final Map<String, Integer> lecturesByNature = new HashMap<String, Integer>();
		final Integer handsOnLectures = this.repository.findNumOfLecturesByType(lecturer, Nature.HANDS_ON).orElse(0);
		final Integer theoreticalLectures = this.repository.findNumOfLecturesByType(lecturer, Nature.THEORETICAL).orElse(0);
		lecturesByNature.put("HANDS_ON", handsOnLectures);
		lecturesByNature.put("THEORETICAL", theoreticalLectures);
		dashboard.setNumOfLecturesByType(lecturesByNature);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "lecturesStats", "numOfLecturesByType", "coursesStats");

		super.getResponse().setData(tuple);
	}

}
