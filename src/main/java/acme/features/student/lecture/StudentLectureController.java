
package acme.features.student.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentLectureController extends AbstractController<Student, Lecture> {

	@Autowired
	protected StudentLectureListService	listService;

	@Autowired
	protected StudentLectureShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
