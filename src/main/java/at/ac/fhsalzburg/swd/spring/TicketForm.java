package at.ac.fhsalzburg.swd.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TicketForm {

    private LocalDate to;
    private LocalDate from;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // GETTERS & SETTERS
    public LocalDate getSqlTo() {
        return to;
    }

    public void setSqlTo(String to) throws ParseException {
        formatter = formatter.withLocale(Locale.GERMAN );
        this.to  = LocalDate.parse(to , formatter);
    }
 
    public LocalDate getSqlFrom() {
        return from;
    }
 
    public void setLastName(String from) throws ParseException {
        formatter = formatter.withLocale(Locale.GERMAN );
        this.from  = LocalDate.parse(from , formatter);
    }
     
}