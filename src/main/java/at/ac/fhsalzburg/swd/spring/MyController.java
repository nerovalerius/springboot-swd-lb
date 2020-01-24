package at.ac.fhsalzburg.swd.spring;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

import java.util.Arrays;
import javax.validation.constraints.Null;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.bind.annotation.*;

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

	// Hier noch singleton
	@Autowired
	TicketSystem ticketSystem;

	// Hier noch singleton
	@Autowired
	PaymentProvider paymentProvider;

	// STATUS MESSAGES
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

		model.addAttribute("customers", customerManagement.getCustomers());
		model.addAttribute("tickets", ticketSystem.getTickets());
		model.addAttribute("payments", paymentProvider.getPayments());

		model.addAttribute("beanSingleton", singletonBean.getHashCode());

		TestBean prototypeBean = context.getBean("prototypeBean", TestBean.class);
		model.addAttribute("beanPrototype", prototypeBean.getHashCode());

		model.addAttribute("beanSession", sessionBean.getHashCode());

		// FILL STUFF WITH VALUES
		if (status.firstStart == true){

			// CUSTOMER
			Customer customer1 = new Customer("ALAN", "TURING");
			customerManagement.addCustomer(customer1);


			Customer customer2= new Customer("DUKE", "NUKEM");
			customerManagement.addCustomer(customer2);

			// TICKETS
			Ticket dummyTicket = new Ticket(LocalDate.now().plusDays(1), LocalDate.now(), customer1);
			ticketSystem.getNewTicket(dummyTicket);
			long id = dummyTicket.getId();

			Ticket dummyTicket2 = new Ticket(LocalDate.now().plusDays(3), LocalDate.now(), customer2);
			ticketSystem.getNewTicket(dummyTicket2);
			long id2 = dummyTicket2.getId();

			// PAYMENTS
			Payment dummyPayment = new Payment(9.5, LocalDate.now(), (int) id, "Paypal");
			Payment dummyPayment2 = new Payment(9.0, LocalDate.now(), (int) id, "Paypal");

			// TICKET PAYMENT
			ticketSystem.payTicket(dummyPayment, id);
			ticketSystem.payTicket(dummyPayment2, id2);

			status.firstStart = false;
		}




		status.currentUserFirstName = "";
		status.currentUserLastName = "";

		return "index";
	}

	/***********************************************************************
	 CUSTOMERS
	 ***********************************************************************/

	@RequestMapping(value = { "/manageCustomers" }, method = RequestMethod.POST)
	public String manageCustomer(Model model, //
							  @ModelAttribute("customerForm") CustomerForm customerForm) {
		status.currentUserFirstName = "";
		status.currentUserLastName = "";
		String firstName = customerForm.getFirstName();
		String lastName = customerForm.getLastName();

		// Is there even a user input?
		if (firstName != null && firstName.length() > 0 //
				&& lastName != null && lastName.length() > 0) {
			// Customer already in list?
			if (customerManagement.getCustomer(firstName, lastName) == null){
				Customer newCustomer = new Customer(firstName, lastName);
				customerManagement.addCustomer(newCustomer);
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
	public String showManageCustomersPage(Model model) {
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
	public String manageTicket(Model model, //
							  @ModelAttribute("ticketForm")TicketForm ticketForm) {


		model.addAttribute("ticketStatus", status.ticketStatus);
		model.addAttribute("currentUser", status.currentUserFirstName + " " + status.currentUserLastName);
		model.addAttribute("tickets", ticketSystem.getTickets());
		model.addAttribute("loginStatus",status.loginStatus);


		LocalDate to = ticketForm.getSqlTo();
		LocalDate from = ticketForm.getSqlFrom();

		if(status.currentUserFirstName.equals("") || status.currentUserLastName.equals("")){

			status.ticketStatus = ("ERROR: Ticket not created! - No User logged in");
			return "redirect:/manageTickets";
		}


		Customer currentCustomer = customerManagement.getCustomer(status.currentUserFirstName, status.currentUserLastName);

		// See if customer is already in list, if not then create
		if(currentCustomer == null){
			currentCustomer = new Customer(status.currentUserFirstName, status.currentUserLastName);
		};

		// CHECK IF GIVE DATE IS VALID
		if (to != null && from != null) {
			Ticket ticket = new Ticket(to, from, currentCustomer);

			ticketSystem.getNewTicket(ticket);
			status.ticketCustomer = currentCustomer.getFirstName() + currentCustomer.getLastName();
			status.ticketStatus = ("Ticket successfully created!");
		} else {
			status.ticketStatus = ("ERROR: Ticket not created! - Date must be in format dd.MM.yyyy");
		}

		return "redirect:/manageTickets";
	}


	@RequestMapping(value = { "/manageTickets" }, method = RequestMethod.GET)
	public String showManageTicketPage(Model model) {
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
	 PAYMENTS
	 ***********************************************************************/

	@RequestMapping(value = { "/managePayments" }, method = RequestMethod.POST)
	public String managePayment(Model model, //
							  @ModelAttribute("paymentForm")PaymentForm paymentForm) {

		model.addAttribute("currentUser", status.currentUserFirstName + " " + status.currentUserLastName);
		model.addAttribute("payments", paymentProvider.getPayments());
		model.addAttribute("loginStatus",status.loginStatus);


		LocalDate date = LocalDate.now();
		double amount = paymentForm.getAmount();
		int ticketId = paymentForm.getTicketId();
		String paymentMethod = paymentForm.getPaymentMethod();

		if(status.currentUserFirstName.equals("") || status.currentUserLastName.equals("")){

			status.ticketStatus = ("ERROR: Ticket not created - No User logged in!");
			return "redirect:/managePayments";
		}

		// CREATE PAYMENT
		Payment currentPayment = new Payment(amount, date, ticketId, paymentMethod);
		paymentProvider.getNewPayment(currentPayment);

		Ticket currentTicket = ticketSystem.getTicket(ticketId);

		// no ticket with this id
		if (currentTicket == null){
			status.paymentStatus = ("ERROR: Ticket not paid! - No Ticket with this id!");
		} else {
			ticketSystem.payTicket(currentPayment, ticketId);
			status.paymentStatus = ("Ticket successfully paid!");
		}

		return "redirect:/managePayments";
	}


	@RequestMapping(value = { "/managePayments" }, method = RequestMethod.GET)
	public String showManagePaymentsPage(Model model) {
		PaymentForm paymentForm = new PaymentForm();

		model.addAttribute("payments", paymentProvider.getPayments());
		model.addAttribute("paymentStatus", status.paymentStatus);
		model.addAttribute("currentUser", status.currentUserFirstName + " " + status.currentUserLastName);
		model.addAttribute("paymentForm", paymentForm);

		return "managePayments";
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


	/***********************************************************************
	 REST MAPPING PAYMENTS
	 ***********************************************************************/

	@GetMapping("/payments")
	public @ResponseBody List<Payment> allPayments() {

		return (List<Payment>) paymentProvider.getPayments();
	}

	@RequestMapping(value = { "/payments/{id}" }, method = RequestMethod.GET)
	public @ResponseBody Payment addPayment(@PathVariable long id) {
		Payment Payment = paymentProvider.getPayment(id);

		return Payment;
	}

	@RequestMapping(value = { "/payments/{id}" }, method = RequestMethod.PUT)
	public String setPayment(@RequestBody Payment Payment) {

		paymentProvider.getNewPayment(Payment);

		return "redirect:/payments";
	}

	@DeleteMapping("/payments/{id}")
	public String deletePayment(@PathVariable String id) {
		Long PaymentId = Long.parseLong(id);
		paymentProvider.deletePaymentById(PaymentId);
		return "redirect:/payments";
	}

}

