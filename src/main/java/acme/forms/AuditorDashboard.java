
package acme.forms;

import java.util.Map;

import acme.datatypes.Nature;
import acme.datatypes.Statistic;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Map<Nature, Integer>		numOfAuditsByType;
	Statistic					numOfAuditingRecordsStats;
	Statistic					periodOfAuditingRecordStats;
}
