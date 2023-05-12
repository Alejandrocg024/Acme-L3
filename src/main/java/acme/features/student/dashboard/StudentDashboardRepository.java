/*
 * AdministratorDashboardRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	/*
	 * @Query("select avg(select count(j) from Job j where j.employer.id = e.id) from Employer e")
	 * Double averageNumberOfJobsPerEmployer();
	 * 
	 * @Query("select avg(select count(a) from Application a where a.worker.id = w.id) from Worker w")
	 * Double averageNumberOfApplicationsPerWorker();
	 * 
	 * @Query("select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id = e.id and a.job.id = j.id)) from Employer e")
	 * Double averageNumberOfApplicationsPerEmployer();
	 * 
	 * @Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.PENDING")
	 * Double ratioOfPendingApplications();
	 * 
	 * @Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.ACCEPTED")
	 * Double ratioOfAcceptedApplications();
	 * 
	 * @Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.REJECTED")
	 * Double ratioOfRejectedApplications();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Query("select avg(l.estimatedLearningTime) from Lecture l where l.lecturer = :lecturer and l.draftMode=false")
	 * Optional<Double> findAverageLectureLearningTime(Lecturer lecturer);
	 * 
	 * @Query("select max(l.estimatedLearningTime) from Lecture l where l.lecturer = :lecturer and l.draftMode=false")
	 * Optional<Double> findMaxLectureLearningTime(Lecturer lecturer);
	 * 
	 * @Query("select min(l.estimatedLearningTime) from Lecture l where l.lecturer = :lecturer and l.draftMode=false")
	 * Optional<Double> findMinLectureLearningTime(Lecturer lecturer);
	 * 
	 * @Query("select stddev(l.estimatedLearningTime) from Lecture l where l.lecturer = :lecturer and l.draftMode=false")
	 * Optional<Double> findLinearDevLectureLearningTime(Lecturer lecturer);
	 * 
	 * 
	 * 
	 * @Query("select ua from UserAccount ua where ua.id = :id")
	 * UserAccount findOneUserAccountById(int id);
	 * 
	 * @Query("select sum(l.estimatedLearningTime) from Course c join CourseLecture cl on c = cl.course join Lecture l on cl.lecture = l where c.lecturer = :lecturer and c.draftMode=false group by c")
	 * Collection<Double> findEstimatedLearningTimeByCourse(Lecturer lecturer);
	 */

	@Query("select s from Student s where s.userAccount.id = :id")
	Student findStudentByUserAccountId(int id);

	@Query("select a.nature, count(a) from Activity a join a.enrolment e join e.student s where s = :student group by a.nature")
	Map<Nature, Integer> findNumOfActivitiesByType(Student student);

}
