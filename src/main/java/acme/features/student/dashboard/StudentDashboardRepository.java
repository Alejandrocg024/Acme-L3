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

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.entities.Activity;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select s from Student s where s.userAccount.id = :id")
	Student findStudentByUserAccountId(int id);

	@Query("select count(a)  from Activity a join a.enrolment e where e.student = :student and a.nature = :nature")
	Optional<Integer> findNumOfActivitiesByStudentAndNature(Student student, Nature nature);

	@Query("select sum(l.estimatedLearningTime) from Enrolment e join Course c on c = e.course join CourseLecture cl on c = cl.course join Lecture l on cl.lecture = l where e.student = :student group by c")
	Collection<Double> findTimesOfEnrolledCoursesByStudent(Student student);

	@Query("select a from Activity a where a.enrolment.student = :student group by a")
	Collection<Activity> findActivitiesByStudent(Student student);

}
