
package acme.forms;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Nature, Integer>		numberOfActivitiesByNature;
	Statistic					periodsOfActivitiesStats;
	Statistic					timesOfEnrolledCoursesStats;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
