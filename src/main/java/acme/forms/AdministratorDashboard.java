
package acme.forms;

import java.util.ArrayList;
import java.util.List;
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
	Map<String, Statistic>		currentsOffersStats;
	Statistic					notesInLast10WeeksStats;


	public void calcNotesInLast10WeeksStats(final Map<Integer, Double> map) {
		Statistic stats;
		stats = new Statistic();
		final List<Double> values = new ArrayList<Double>();
		values.addAll(map.values());
		stats.calcAverage(values);
		stats.calcMax(values);
		stats.calcMin(values);
		stats.calcLinDev(values);
	}
}
