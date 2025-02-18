package at.ac.fhsalzburg.swd.spring;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Entity
public class Ticket {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private LocalDate sqlTo;		// HIBERNATE ERROR WHEN NAME WITHOUT SQL IN IT - CLASS DIAGRAMM IS JUST (TO,FROM)
    private LocalDate sqlFrom;

	@OneToOne(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL)
	private Customer customer;		// TO WHICH CUSTOMER DOES THIS TICKET BELONG? NOT IN CLASS DIAGRAM

	@OneToOne(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL)
	private Payment payment;		// WHAT PAYMENT FULLY PAID THE TICKET? NOT IN CLASS DIAGRAM

	private double price;			// MAKES SENSE - NOT IN CLASS DIAGRAM
	private double outstanding_payment;				// NOT IN CLASS DIAGRAM BUT MAKES SENSE
	private String type;			// MAKES SENSE - NOT IN CLASS DIAGRAM
	private String paymentMethod;	// MAKES SENSE - NOT IN CLASS DIAGRAM
	private boolean paid;			// MAKES SENSE - NOT IN CLASS DIAGRAM


	// GETTERS & SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getTo() {
		return sqlTo;
	}

	public void setTo(LocalDate to) {
		this.sqlTo = to;
	}

	public LocalDate getFrom() {
		return sqlFrom;
	}

	public void setFrom(LocalDate from) {
		this.sqlFrom = from;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;

	}

	public String getFirstName() {
		return this.customer.getFirstName();
	}

	public void setFirstName(String firstName) {
		this.customer.setFirstName(firstName);
	}

	public String getLastName() {
		return this.customer.getLastName();
	}

	public void setLastName(String lastName) {
		this.customer.setLastName(lastName);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.outstanding_payment -= payment.getAmount();
		this.payment = payment;
		this.paymentMethod = payment.getPaymentMethod();

		if (outstanding_payment <= 0) {
			this.paid = true;
		} else {
			this.paid = false;
		}

	}

	public boolean getPaid() {
		return paid;
	}

	public double getOutstanding_payment() {
		return outstanding_payment;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	// FUNCTIONS
	private double calculatePrice(LocalDate from, LocalDate to){
		// Calculate the difference in days
		long days_difference = ChronoUnit.DAYS.between(to, from);

		if(days_difference < 0){
			days_difference *= -1;
		}

		return days_difference * 9.50;
	}


	// CONSTRUCTORS
	public Ticket(LocalDate to, LocalDate from, Customer customer){
		this.customer = customer;
		this.customer.setFirstName(customer.getFirstName());
		this.customer.setLastName(customer.getLastName());
		this.sqlTo = to;
		this.sqlFrom = from;
		this.price = calculatePrice(to, from);
		this.outstanding_payment = this.price;
		this.paid = false;

		// Calculate the difference in days
		long days_difference = ChronoUnit.DAYS.between(from, to);

		// Longer than 2 days = permanent ticket
		if (days_difference > 2){
			this.setType("permanent");
		} else {
			this.setType("non-permanent");
		}
	}

	protected Ticket() {}


    /*
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
    */



}
