package at.ac.fhsalzburg.swd.spring;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class TicketForm {

    private LocalDate to;
    private LocalDate from;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // GETTERS & SETTERS
    public LocalDate getSqlTo() {
        return to;
    }

    public boolean setSqlTo(String to) throws ParseException {
        try {
            this.to = LocalDate.parse(to, this.formatter);
            return true;
        } catch (DateTimeParseException e) {
            this.to = null;
            return false;
        }
    }

    public LocalDate getSqlFrom() {
        return from;
    }

    public boolean setSqlFrom(String from) throws ParseException {
        try {
            this.from = LocalDate.parse(from, this.formatter);
            return true;
        } catch (DateTimeParseException e) {
            this.from = null;
            return false;
        }
    }
     
}