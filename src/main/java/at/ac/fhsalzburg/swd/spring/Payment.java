package at.ac.fhsalzburg.swd.spring;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


	private double amount;
	private LocalDate sqlDate;
	private int ticketId;
	private String paymentMethod;


    // GETTERS
	public Long getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(LocalDate sqlDate) {
		this.sqlDate = sqlDate;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	// CONSTRUCTORS
	protected Payment() {}

	public Payment(double amount, LocalDate date, int ticketId, String paymentMethod) {
		this.amount = amount;
		this.sqlDate = date;
		this.ticketId = ticketId;
		this.paymentMethod = paymentMethod;
	}



}
