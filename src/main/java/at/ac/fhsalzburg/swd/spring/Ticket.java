package at.ac.fhsalzburg.swd.spring;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Ticket {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private LocalDate sqlTo;		// Ich habe wirklich alles mögliche probiert.    @Temporal(TemporalType.TIMESTAMP)^...  @Temporal(TemporalType.DATE)...
    private LocalDate sqlFrom;		// erst wenn das wort sql im variablen namen drinnen ist wird das sql statement korrekt erstellt, ansonsten gibt es eine hibernate h2 exception: expected identifier...
	
	
	
	public LocalDate getSqlDate() {
		return Date;
	}

	public void setSqlDate(LocalDate sqlDate) {
		this.Date = sqlDate;
	}

	private LocalDate Date;
    private String type;


    // CONSTRUCTORS
    protected Ticket() {}

    public Ticket(LocalDate to, LocalDate from, String type) {
        this.sqlTo = to;
        this.sqlFrom = from;
        this.type = type;

    }

	public Ticket(Ticket ticket) {
    	this.id = ticket.id;
    	this.sqlTo = ticket.sqlTo;			// Funktioniert ohne sql im Namen nicht, weder mit java.util.date noch LocalDate
    	this.sqlFrom = ticket.sqlFrom;
    	this.type = ticket.type;
	}


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

	public void setFrom(LocalDate From) {
		this.sqlFrom = From;
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
