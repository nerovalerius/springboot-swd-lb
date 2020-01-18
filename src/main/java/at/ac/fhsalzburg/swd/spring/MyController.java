package at.ac.fhsalzburg.swd.spring;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

	@Autowired
	private ApplicationContext context;

	@Resource(name = "sessionBean")
	TestBean sessionBean;

	@Autowired
	TestServiceI testService;

	@Autowired
	TestBean singletonBean;

	// Hier noch singleton
	@Autowired
	CustomerManagement customerManagement;

	@Autowired
	TicketSystem ticketSystem;

	Status status = new Status();


	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		if (session==null)
		{
			model.addAttribute("message","no session");
		}
		else
		{
			Integer count = (Integer) session.getAttribute("count");
			if (count==null)
			{
				count = 0;
			}
			count++;
			session.setAttribute("count", count);
		}

		model.addAttribute("message",testService.doSomething());

		model.addAttribute("halloNachricht","welcome to SWD lab");

		Customer sepp = new Customer("dummy", "dummy");
		customerManagement.addCustomer(sepp);

		Ticket dummyTicket = new Ticket(LocalDate.now(), LocalDate.now(), "dummy");
		ticketSystem.getNewTicket(dummyTicket);

		model.addAttribute("customers", customerManagement.getCustomers());
		model.addAttribute("tickets", ticketSystem.getTickets());

		model.addAttribute("beanSingleton", singletonBean.getHashCode());

		TestBean prototypeBean = context.getBean("prototypeBean", TestBean.class);
		model.addAttribute("beanPrototype", prototypeBean.getHashCode());

		model.addAttribute("beanSession", sessionBean.getHashCode());

		//status.currentUserFirstName = "";
		//status.currentUserLastName = "";

		return "index";
	}

	/***********************************************************************
	 CUSTOMERS
	 ***********************************************************************/

	@RequestMapping(value = { "/manageCustomers" }, method = RequestMethod.POST)
	public String addCustomer(Model model, //
							  @ModelAttribute("customerForm") CustomerForm customerForm) {
		status.currentUserFirstName = "";
		status.currentUserLastName = "";
		String firstName = customerForm.getFirstName();
		String lastName = customerForm.getLastName();

		// Is there even a user input?
		if (firstName != null && firstName.length() > 0 //
				&& lastName != null && lastName.length() > 0) {
			// Customer already in list?
			if (customerManagement.getCustomer(lastName).isEmpty()){
				Customer newCustomer = new Customer(firstName, lastName);
				customerManagement.addCustomer(newCustomer);
				customerManagement.setCurrentCustomer(newCustomer);
				status.loginStatus = " created and logged in";
			} else {
				status.loginStatus = " logged in";
			}
			status.currentUserFirstName = firstName;
			status.currentUserLastName = lastName;
			return "redirect:/manageTickets";
		}

		return "redirect:/manageCustomers";
	}

	@RequestMapping(value = { "/manageCustomers" }, method = RequestMethod.GET)
	public String showAddPersonPage(Model model) {
		CustomerForm customerForm = new CustomerForm();

		model.addAttribute("customers", customerManagement.getCustomers());
		model.addAttribute("tickets", ticketSystem.getTickets());

		model.addAttribute("customerForm", customerForm);
		model.addAttribute("message",testService.doSomething());

		return "manageCustomers";
	}

	/***********************************************************************
	 TICKETS
	 ***********************************************************************/

	@RequestMapping(value = { "/manageTickets" }, method = RequestMethod.POST)
	public String orderTicket(Model model, //
							  @ModelAttribute("ticketForm")TicketForm ticketForm) {


		model.addAttribute("ticketStatus", status.ticketStatus);
		model.addAttribute("currentUser", status.currentUserFirstName + " " + status.currentUserLastName);
		model.addAttribute("tickets", ticketSystem.getTickets());
		model.addAttribute("loginStatus",status.loginStatus);

		LocalDate to = ticketForm.getSqlTo();
		LocalDate from = ticketForm.getSqlFrom();
		Customer currentCustomer = customerManagement.addCustomer(new Customer("sepp", "depp"));

		//if (to.equals(null)  && from.equals(null)) {
			Ticket ticket = new Ticket(to, from, currentCustomer);
/*
			// Calculate the difference in days
			long days_difference = to.getDayOfYear() - from.getDayOfYear();
			days_difference = TimeUnit.DAYS.convert(days_difference, TimeUnit.MILLISECONDS);

			// Longer than 5 days = permanent ticket
			if (days_difference > 5){
				ticket.setType("permanent");
			} else {
				ticket.setType("non-permanent");
			}
*/
			ticketSystem.getNewTicket(ticket);
			status.ticketCustomer = currentCustomer.getFirstName() + currentCustomer.getLastName();
			status.ticketStatus = ("Ticket successfully created!");
		//} else {
		//	status.ticketStatus = ("ERROR: Ticket not created!");
		//}

		return "redirect:/manageTickets";
	}


	@RequestMapping(value = { "/manageTickets" }, method = RequestMethod.GET)
	public String showOrderTicketPage(Model model) {
		TicketForm ticketForm = new TicketForm();

		model.addAttribute("customers", customerManagement.getCustomers());
		model.addAttribute("tickets", ticketSystem.getTickets());

		model.addAttribute("ticketForm", ticketForm);
		model.addAttribute("message",testService.doSomething());
		model.addAttribute("ticketStatus", status.ticketStatus);
		model.addAttribute("ticketCustomer", status.ticketCustomer);
		model.addAttribute("loginStatus",status.loginStatus);
		model.addAttribute("currentUser", status.currentUserFirstName + " " + status.currentUserLastName);


		return "manageTickets";
	}



	/***********************************************************************
	 REST MAPPING CUSTOMERS
	 ***********************************************************************/

	@GetMapping("/customers")
	public @ResponseBody List<Customer> allUsers() {

		return (List<Customer>) customerManagement.getCustomers();
	}

	@RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.GET)
	public @ResponseBody Customer addCustomer(@PathVariable long id) {
		Customer customer = customerManagement.getCustomer(id);

		return customer;
	}

	@RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.PUT)
	public String setCustomer(@RequestBody Customer customer) {

		customerManagement.addCustomer(customer);

		return "redirect:/customers";
	}

	@DeleteMapping("/customers/{id}")
	public String delete(@PathVariable String id) {
		Long customerid = Long.parseLong(id);
		customerManagement.deleteCustomerById(customerid);
		return "redirect:/customers";
	}

	/***********************************************************************
	 REST MAPPING TICKETS
	 ***********************************************************************/

	@GetMapping("/tickets")
	public @ResponseBody List<Ticket> allTickets() {

		return (List<Ticket>) ticketSystem.getTickets();
	}

	@RequestMapping(value = { "/tickets/{id}" }, method = RequestMethod.GET)
	public @ResponseBody Ticket addTicket(@PathVariable long id) {
		Ticket ticket = ticketSystem.getTicket(id);

		return ticket;
	}

	@RequestMapping(value = { "/tickets/{id}" }, method = RequestMethod.PUT)
	public String setTicket(@RequestBody Ticket ticket) {

		ticketSystem.getNewTicket(ticket);

		return "redirect:/tickets";
	}

	@DeleteMapping("/tickets/{id}")
	public String deleteTicket(@PathVariable String id) {
		Long ticketId = Long.parseLong(id);
		ticketSystem.deleteTicketById(ticketId);
		return "redirect:/tickets";
	}


}

