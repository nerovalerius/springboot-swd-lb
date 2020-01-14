package at.ac.fhsalzburg.swd.spring;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Ticket {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Date to;
    private Date from;
    private String type;


    // CONSTRUCTORS
    protected Ticket() {}

    public Ticket(Date to, Date from, String type) {
        this.to = to;
        this.from = from;
        this.type = type;

    }

	public Ticket(Ticket ticket) {
    	this.id = ticket.id;
    	this.to = ticket.to;
    	this.from = ticket.from;
    	this.type = ticket.type;
	}


	// GETTERS & SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}




    /*
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
    */



}
