package at.ac.fhsalzburg.swd.spring;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class PaymentForm {

    // VARIABLES
    private double amount;
    private int ticketId;
    private String paymentMethod;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

}