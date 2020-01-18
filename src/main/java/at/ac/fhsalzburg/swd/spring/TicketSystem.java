package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketSystem {

    @Autowired
    private TicketRepository ticket_repository;

    private int utilization;
    private int handicappedUtilization;



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

    // GET NEW TICKET - In class diagram referred to getNewTicket(customer Customer)
    Ticket getTicket(long id){
        return ticket_repository.findById(id);
    };

    // GET ALL TICKETS
    List <Ticket> getTickets(){
        return (List<Ticket>) ticket_repository.findAll();
    }

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
