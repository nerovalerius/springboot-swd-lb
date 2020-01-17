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
@EnableJpaRepositories(basePackageClasses= {CustomerRepository.class, TicketRepository.class})
public class MyController {

	@Autowired
	private ApplicationContext context;

	@Resource(name = "sessionBean")
	TestBean sessionBean;

	@Autowired
	TestServiceI testService;

	@Autowired //don't forget the setter
	private CustomerRepository customer_repository;

	@Autowired //don't forget the setter
	private TicketRepository ticket_repository;


	@Autowired
	TestBean singletonBean;

	CustomerManagement customerManagement = new CustomerManagement(ticket_repository, customer_repository);
	TicketSystem ticketSystem = new TicketSystem(ticket_repository, customer_repository);


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

		model.addAttribute("customers", customer_repository.findAll());
		model.addAttribute("tickets", ticket_repository.findAll());

		model.addAttribute("beanSingleton", singletonBean.getHashCode());

		TestBean prototypeBean = context.getBean("prototypeBean", TestBean.class);
		model.addAttribute("beanPrototype", prototypeBean.getHashCode());

		model.addAttribute("beanSession", sessionBean.getHashCode());


		return "index";
	}

	/***********************************************************************
	 CUSTOMERS
	 ***********************************************************************/

	@RequestMapping(value = { "/manageCustomers" }, method = RequestMethod.POST)
	public String addCustomer(Model model, //
							  @ModelAttribute("customerForm") CustomerForm customerForm) {

		String firstName = customerForm.getFirstName();
		String lastName = customerForm.getLastName();

		// Is there even a user input?
		if (firstName != null && firstName.length() > 0 //
				&& lastName != null && lastName.length() > 0) {
			// Customer already in list?
			if (customer_repository.findByLastName(lastName).isEmpty()){
				Customer newCustomer = new Customer(firstName, lastName);
				customer_repository.save(newCustomer);
			}

		}

		return "redirect:/";
	}

	@RequestMapping(value = { "/manageCustomers" }, method = RequestMethod.GET)
	public String showAddPersonPage(Model model) {
		CustomerForm customerForm = new CustomerForm();

		model.addAttribute("customers", customer_repository.findAll());
		model.addAttribute("tickets", ticket_repository.findAll());

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

		LocalDate to = ticketForm.getSqlTo();
		LocalDate from = ticketForm.getSqlFrom();

		if (to != null  && from != null) {
			Ticket ticket = new Ticket();

			// Calculate the difference in days
			long days_difference = to.getDayOfYear() - from.getDayOfYear();
			days_difference = TimeUnit.DAYS.convert(days_difference, TimeUnit.MILLISECONDS);
			ticket.setFrom(from);
			ticket.setTo(to);

			// Longer than 5 days = permanent ticket
			if (days_difference > 5){
				ticket.setType("permanent");
			} else {
				ticket.setType("non-permanent");
			}

				Ticket newCustomer = new Ticket(ticket);
				ticket_repository.save(newCustomer);

		}

		return "redirect:/";
	}


	@RequestMapping(value = { "/manageTickets" }, method = RequestMethod.GET)
	public String showOrderTicketPage(Model model) {
		TicketForm ticketForm = new TicketForm();

		model.addAttribute("customers", customer_repository.findAll());
		model.addAttribute("tickets", ticket_repository.findAll());

		model.addAttribute("ticketForm", ticketForm);
		model.addAttribute("message",testService.doSomething());

		return "manageTickets";
	}



	/***********************************************************************
	 REST MAPPING CUSTOMERS
	 ***********************************************************************/

	@GetMapping("/customers")
	public @ResponseBody List<Customer> allUsers() {

		return (List<Customer>) customer_repository.findAll();
	}

	@RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.GET)
	public @ResponseBody Customer addCustomer(@PathVariable long id) {
		Customer customer = customer_repository.findById(id);

		return customer;
	}

	@RequestMapping(value = { "/customers/{id}" }, method = RequestMethod.PUT)
	public String setCustomer(@RequestBody Customer customer) {

		customer_repository.save(customer);

		return "redirect:/customers";
	}

	@DeleteMapping("/customers/{id}")
	public String delete(@PathVariable String id) {
		Long customerid = Long.parseLong(id);
		customer_repository.deleteById(customerid);
		return "redirect:/customers";
	}

	/***********************************************************************
	 REST MAPPING TICKETS
	 ***********************************************************************/

	@GetMapping("/tickets")
	public @ResponseBody List<Ticket> allTickets() {

		return (List<Ticket>) ticket_repository.findAll();
	}

	@RequestMapping(value = { "/tickets/{id}" }, method = RequestMethod.GET)
	public @ResponseBody Ticket addTicket(@PathVariable long id) {
		Ticket ticket = ticket_repository.findById(id);

		return ticket;
	}

	@RequestMapping(value = { "/tickets/{id}" }, method = RequestMethod.PUT)
	public String setTicket(@RequestBody Ticket ticket) {

		ticket_repository.save(ticket);

		return "redirect:/tickets";
	}

	@DeleteMapping("/tickets/{id}")
	public String deleteTicket(@PathVariable String id) {
		Long ticketId = Long.parseLong(id);
		ticket_repository.deleteById(ticketId);
		return "redirect:/tickets";
	}


}

