package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TicketSystem {

    @Autowired //don't forget the setter
    private TicketRepository ticket_repository;
    private CustomerRepository customerRepository;

    private int utilization;
    private int handicappedUtilization;


    // GET UTILIZATION
    public int getUtilization() {
        return utilization;
    }


    // GET NEW TICKET - In class diagram referred to getNewTicket(customer Customer)
    Ticket getNewTicket(Ticket ticket){
        ticket_repository.save(ticket);
        return ticket_repository.findByTicket(ticket);
    };


    // GET HANDICAPPED UTILIZATION
    int getHandicappedUtilization(){
        return this.handicappedUtilization;
    }


    // VERIFY TICKET
    boolean verifyTicket(Ticket ticket){

        long current_id = ticket.getId();
        Ticket returned_ticket = ticket_repository.findById(current_id);

        return (returned_ticket.getId() == ticket.getId());
    }


    // GETTER & SETTER
    public void setHandicappedUtilization(int handicappedUtilization) {
        this.handicappedUtilization = handicappedUtilization;
    }

    public void setUtilization(int utilization) {
        this.utilization = utilization;
    }


    // CONSTRUCTORS
    TicketSystem(){
        this.handicappedUtilization = 0;
    }

    TicketSystem(TicketRepository ticket_repository, CustomerRepository customer_repository){
        this.ticket_repository = ticket_repository;
        this.customerRepository = customer_repository;
    }



}
