package at.ac.fhsalzburg.swd.spring;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Ticket {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private java.sql.Date sqlTo;		// Ich habe wirklich alles mögliche probiert.    @Temporal(TemporalType.TIMESTAMP)^...  @Temporal(TemporalType.DATE)...
    private java.sql.Date sqlFrom;		// erst wenn das wort sql im variablen namen drinnen ist wird das sql statement korrekt erstellt, ansonsten gibt es eine hibernate h2 exception: expected identifier...

	public Date getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	private java.sql.Date sqlDate;
    private String type;


    // CONSTRUCTORS
    protected Ticket() {}

    public Ticket(java.sql.Date sqlTo, java.sql.Date sqlFrom, String type) {
        this.sqlTo = sqlTo;
        this.sqlFrom = sqlFrom;
        this.type = type;

    }

	public Ticket(Ticket ticket) {
    	this.id = ticket.id;
    	this.sqlTo = ticket.sqlTo;
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

	public java.sql.Date getSqlTo() {
		return sqlTo;
	}

	public void setSqlTo(java.sql.Date to) {
		this.sqlTo = to;
	}

	public java.sql.Date getSqlFrom() {
		return sqlFrom;
	}

	public void setSqlFrom(java.sql.Date sqlFrom) {
		this.sqlFrom = sqlFrom;
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
