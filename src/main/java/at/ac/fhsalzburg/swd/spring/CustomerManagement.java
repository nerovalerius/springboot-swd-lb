package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CustomerManagement {

    @Autowired //don't forget the setter
    private CustomerRepository customer_repository;
    private TicketRepository ticket_repository;

    // ADD CUSTOMER
    void addCustomer(Customer customer){
            // Customer already in list?
            if (customer_repository.findByLastName(customer.getLastName()).isEmpty()){
                customer_repository.save(customer);
            }
    };

    // GET CUSTOMER BY ID
    Customer getCustomer(long id){
        return customer_repository.findById(id);
    };

    // GET CUSTOMER BY LAST NAME - Inside the class diagram referred to as "getCustomer(licence String)"
    List<Customer> getCustomer(String lastName){
        return customer_repository.findByLastName(lastName);
    }

    // GET TICKETS OF CUSTOMER
    List<Ticket> getTicketsOfCustomer(Customer customer){
        return customer.getTickets();
    }


    // GET CUSTOMER TYPE
    String getCustomer(Customer customer){

        List<Customer> customer_list = customer_repository.findByLastName(customer.getLastName());

        if (customer_list.isEmpty()){
            return null;
        } else {
            return customer_list.get(0).getType();
        }
    };


    // CONSTRUCTOR
    CustomerManagement(TicketRepository ticket_repository, CustomerRepository customer_repository){
        this.ticket_repository = ticket_repository;
        this.customer_repository = customer_repository;
    }


}
