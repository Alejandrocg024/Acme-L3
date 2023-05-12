/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.dashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.forms.StudentDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashboardRepository repository;

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
		StudentDashboard dashboard;
		final Student student;

		final Map<Nature, Integer> numberOfActivitiesByNature;
		final Integer handsOnActivities;
		final Integer balancedActivities;
		final Integer theoreticalActivities;

		final Statistic activitiesPeriodStats;
		final Statistic coursesTimeStats;

		student = this.repository.findStudentByUserAccountId(super.getRequest().getPrincipal().getAccountId());

		//numberOfActivitiesByNature
		numberOfActivitiesByNature = this.repository.findNumOfActivitiesByType(student);
		//numberOfActivitiesByNature = new HashMap<Nature, Integer>();

		//handsOnActivities = this.repository.findNumOfActivitiesByType(student, Nature.HANDS_ON).orElse(0);
		//numberOfActivitiesByNature.put(Nature.HANDS_ON, handsOnActivities);

		//balancedActivities = this.repository.findNumOfActivitiesByType(student, Nature.BALANCED).orElse(0);
		//numberOfActivitiesByNature.put(Nature.BALANCED, balancedActivities);

		//theoreticalActivities = this.repository.findNumOfActivitiesByType(student, Nature.THEORETICAL).orElse(0);
		//numberOfActivitiesByNature.put(Nature.THEORETICAL, theoreticalActivities);

		//activitiesPeriodStats
		//coursesTimeStats
		dashboard = new StudentDashboard();
		dashboard.setNumberOfActivitiesByNature(numberOfActivitiesByNature);
		//dashboard.setActivitiesPeriodStats(activitiesPeriodStats);
		//dashboard.setCoursesTimeStats(coursesTimeStats);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"averageNumberOfJobsPerEmployer", "averageNumberOfApplicationsPerWorker", // 
			"avegageNumberOfApplicationsPerEmployer", "ratioOfPendingApplications", //
			"ratioOfRejectedApplications", "ratioOfAcceptedApplications");

		super.getResponse().setData(tuple);
	}

}
