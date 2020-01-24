package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
public class TicketSystem {  // By default Service annotation creates a singleton scoped bean - SINGLETON IS USED HERE

    @Autowired
    private TicketRepository ticket_repository;

    private int utilization;
    private int handicappedUtilization;

    @Autowired
    CustomerManagement customerManagement;

    // GET UTILIZATION
    public int getUtilization() {
        return utilization;
    }


    // GET NEW TICKET - In class diagram referred to getNewTicket(customer Customer)
    Ticket getNewTicket(Ticket ticket){
        // Ticket already in list?
        try {
            if (ticket_repository.findById(ticket.getId()).isEmpty()){
                ticket_repository.save(ticket);
            }
        }
        catch(Exception e) {
            ticket_repository.save(ticket);
        }

        ticket_repository.save(ticket);
        //return ticket_repository.findByTicket(ticket);
        return new Ticket();
    };

    // GET HANDICAPPED UTILIZATION
    int getHandicappedUtilization(){
        return this.handicappedUtilization;
    }

    // GET NEW TICKET - In class diagram referred to getNewTicket(customer Customer)
    Ticket getTicket(long id){
        return ticket_repository.findById(id);
    };

    // GET NEW TICKET - In class diagram referred to getNewTicket(customer Customer)
    List<Ticket> getTicketsByCustomerId(long customerId){
        Customer tmpCustomer = customerManagement.getCustomer(customerId);
        return ticket_repository.findByCustomer(tmpCustomer);
    };

    // GET ALL TICKETS                                                          // NOT IN CLASS DIAGRAM
    List <Ticket> getTickets(){
        return (List<Ticket>) ticket_repository.findAll();
    }
    

    // VERIFY TICKET
    boolean verifyTicket(Ticket ticket){
        LocalDate from = ticket.getFrom();
        LocalDate to = ticket.getTo();
        LocalDate now = LocalDate.now();

        if(from.compareTo(now) <= 0 && to.compareTo(now) > 0) {
            return true;
        }

        return false;
    }

    // ADD PAYMENT
    void payTicket(Payment payment, long ticketId){
        Ticket currentTicket = ticket_repository.findById(ticketId);
            currentTicket.setPayment(payment);
            ticket_repository.save(currentTicket);
    }

    // GETTER & SETTER
    public void setHandicappedUtilization(int handicappedUtilization) {
        this.handicappedUtilization = handicappedUtilization;
    }

    public void setUtilization(int utilization) {
        this.utilization = utilization;
    }

    // DELETE CUSTOMER
    void deleteTicketById(long id){
        ticket_repository.deleteById(id);
    }


    // CONSTRUCTORS
    TicketSystem(){
        this.handicappedUtilization = 0;
    }

    TicketSystem(TicketRepository ticket_repository){
        this.ticket_repository = ticket_repository;
    }



}
