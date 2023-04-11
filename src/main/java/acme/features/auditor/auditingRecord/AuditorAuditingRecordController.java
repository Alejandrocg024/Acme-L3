
package acme.features.auditor.auditingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.AuditingRecord;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditingRecordController extends AbstractController<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordListService		listAllService;

	@Autowired
	protected AuditorAuditingRecordShowService		showService;

	@Autowired
	protected AuditorAuditingRecordCreateService	createService;

	@Autowired
	protected AuditorAuditingRecordDeleteService	deleteService;

	@Autowired
	protected AuditorAuditingRecordUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
