
package acme.forms;

import java.util.Map;

import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Map<String, Integer>		principalsByRole;
	Double						peepsRatioWithLinkAndEmail;
	Map<Boolean, Double>		ratioOfBulletinsByCriticality;
	Statistic					currentsOffersStats;
	Statistic					notesInLast10WeeksStats;
}
