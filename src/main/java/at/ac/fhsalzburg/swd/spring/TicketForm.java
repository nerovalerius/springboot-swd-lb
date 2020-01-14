package at.ac.fhsalzburg.swd.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketForm {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
    private Date to;
    private Date  from;



    // GETTERS & SETTERS
    public Date to() {
        return to;
    }
 
    public void setFirstName(String to) throws ParseException {
        this.to = dateFormat.parse(to);
    }
 
    public Date from() {
        return from;
    }
 
    public void setLastName(String from) throws ParseException {
        this.to = dateFormat.parse(from);
    }
     
}