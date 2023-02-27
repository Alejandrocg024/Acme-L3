
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						numberOfAssistants;
	Integer						numberOfAuditors;
	Integer						numberOfCompanys;
	Integer						numberOfConsumers;
	Integer						numberOfProviders;
	Integer						numberOfLecturers;
	Double						linkAndEmailPeepsRatio;
	Double						criticalBulletinsRatio;
	Double						nonCriticalBulletinsRatio;
	Double						averageBudgetOfCurrentsOffers;
	Double						minimumBudgetOfCurrentsOffers;
	Double						maximumBudgetOfCurrentsOffers;
	Double						deviationBudgetOfCurrentOffers;
	Double						averageOfNotesInLast10Weeks;
	Double						minimumOfNotesInLast10Weeks;
	Double						maximumOfNotesInLast10Weeks;
	Double						deviationOfNotesInLast10Weeks;
}
