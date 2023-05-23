
package acme.features.administrator.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorOfferController extends AbstractController<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferListService		listService;

	@Autowired
	protected AdministratorOfferShowService		showService;

	@Autowired
	protected AdministratorOfferPostService	postService;

	@Autowired
	protected AdministratorOfferDeleteService	deleteService;

	@Autowired
	protected AdministratorOfferUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.postService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
