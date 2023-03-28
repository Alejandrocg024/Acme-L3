
package acme.features.lecturer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.Nature;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(distinct l)  from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer and l.nature = :nature")
	Integer findNumOfLecturesByType(Lecturer lecturer, Nature nature);

	@Query("select avg(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	double findAverageLectureLearningTime(Lecturer lecturer);

	@Query("select max(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	double findMaxLectureLearningTime(Lecturer lecturer);

	@Query("select min(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	double findMinLectureLearningTime(Lecturer lecturer);

	@Query("select stddev(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	double findLinearDevLectureLearningTime(Lecturer lecturer);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	//@Query("select avg(estimatedLearningTime) as averageLearningTime from(select sum(l.estimatedLearningTime) as estimatedLearningTime from Course c join CourseLecture cl on c.id = cl.course_id join Lecture l on cl.lecture_id = l.id where c.lecturer = :lecturer group by c.id ) as courseLearningTime")
	//double findAverageCourseLearningTime(Lecturer lecturer);

	//@Query("select max(total_time) from select sum(l.estimatedLearningTime) as total_time from Lecture l join CourseLecture cl on l.id = cl.lecture_id join Course c on c.id = cl.course_id")
	//double findMaxCourseLearningTime(Lecturer lecturer);

	//@Query("select min(total_time) from select sum(l.estimatedLearningTime) as total_time from Lecture l join CourseLecture cl on l.id = cl.lecture_id join Course c on c.id = cl.course_id")
	//double findMinCourseLearningTime(Lecturer lecturer);

	//@Query("select stddev(total_time) from select sum(l.estimatedLearningTime) as total_time from Lecture l join CourseLecture cl on l.id = cl.lecture_id join Course c on c.id = cl.course_id")
	//double findLinDevCourseLearningTime(Lecturer lecturer);

	//select avg(courseLearningTime) from(
	//select sum(l.estimatedLearningTime) as courseLearningTime 
	//from Course c 
	//join CourseLecture cl on cl.course = c
	//join Lecture l on cl.lecture = l 
	//where c.lecturer.id =245 group by c.id
	//)as coursesLearningTime;
}
