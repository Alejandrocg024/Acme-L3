
package acme.features.lecturer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
import acme.forms.LecturerDashboard;
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
		LecturerDashboard dashboard;
		final Double averageLectureLearningTime;
		final Double maxLectureLearningTime;
		final Double minLectureLearningTime;
		final Double linDevOfLecturesLearningTime;

		averageLectureLearningTime = this.repository.averageLectureLearningTime();
		maxLectureLearningTime = this.repository.maxLectureLearningTime();
		minLectureLearningTime = this.repository.minLectureLearningTime();
		linDevOfLecturesLearningTime = this.repository.linDevOfLecturesLearningTime();

		dashboard = new LecturerDashboard();
		final Statistic lectureStats = new Statistic();
		lectureStats.setAverage(averageLectureLearningTime);
		lectureStats.setMax(maxLectureLearningTime);
		lectureStats.setMin(minLectureLearningTime);
		lectureStats.setLinDev(linDevOfLecturesLearningTime);
		dashboard.setLecturesStats(lectureStats);
		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"averageLectureLearningTime", "maxLectureLearningTime",//
			"minLectureLearningTime", "linDevOfLecturesLearningTime");

		super.getResponse().setData(tuple);
	}

}
