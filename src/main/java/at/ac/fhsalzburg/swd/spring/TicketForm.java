package at.ac.fhsalzburg.swd.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketForm {

    private java.sql.Date sqlTo;
    private java.sql.Date sqlFrom;


    // GETTERS & SETTERS
    public java.sql.Date getSqlTo() {
        return sqlTo;
    }

    public void setSqlTo(String to) throws ParseException {
        java.util.Date dateFormat = new SimpleDateFormat("dd MMM yyyy").parse("01 NOVEMBER 2012");
        this.sqlTo = new java.sql.Date(dateFormat.getTime());
    }
 
    public java.sql.Date getSqlFrom() {
        return sqlFrom;
    }
 
    public void setLastName(String from) throws ParseException {
        java.util.Date dateFormat = new SimpleDateFormat("dd MMM yyyy").parse("01 NOVEMBER 2012");
        this.sqlFrom = new java.sql.Date(dateFormat.getTime());
    }
     
}