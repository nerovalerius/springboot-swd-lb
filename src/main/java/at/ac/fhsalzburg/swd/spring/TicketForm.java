package at.ac.fhsalzburg.swd.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class TicketForm {

    private LocalDate To;
    private LocalDate From;


    // GETTERS & SETTERS
    public LocalDate getSqlTo() {
        return To;
    }

    public void setSqlTo(String to) throws ParseException {
        this.To  = LocalDate.of(Integer.parseInt("0"), Integer.parseInt("0"), Integer.parseInt("0"));
    }
 
    public LocalDate getSqlFrom() {
        return From;
    }
 
    public void setLastName(String from) throws ParseException {
        this.From  = LocalDate.of(Integer.parseInt("0"), Integer.parseInt("0"), Integer.parseInt("0"));
    }
     
}