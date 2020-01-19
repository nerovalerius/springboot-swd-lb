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

    private LocalDate sqlTo;		// ohne sql im variablen namen funktionierts nicht, exception...
    private LocalDate sqlFrom;

	@OneToOne(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL)
	private Customer customer;

	private double price;
	private String type;
	private String firstName;
	private String lastName;


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
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.sqlTo = to;
		this.sqlFrom = from;
		this.price = calculatePrice(to, from);
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
