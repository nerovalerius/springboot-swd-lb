package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManagement {

    @Autowired
    private CustomerRepository customer_repository;

    Customer currentCustomer;

    // ADD CUSTOMER
    Customer addCustomer(Customer customer){
        // Customer already in list?
        try {
            if (customer_repository.findByLastName(customer.getLastName()).isEmpty()){
                customer_repository.save(customer);
            }
        }
        catch(Exception e) {
            customer_repository.save(customer);
        }

       return customer;
    };

    // GET CUSTOMER BY ID
    Customer getCustomer(long id){
        return customer_repository.findById(id);
    };

    // GET CUSTOMER BY CUSTOMER
    List<Customer> getCustomer(Customer customer){
        return customer_repository.findByLastName(customer.getLastName());
    }

    // GET ALL CUSTOMERS
    List<Customer> getCustomers(){
        return (List<Customer>) customer_repository.findAll();
    };

    // GET CUSTOMER BY LAST NAME - Inside the class diagram referred to as "getCustomer(licence String)"
    List<Customer> getCustomer(String lastName){
        return customer_repository.findByLastName(lastName);
    }

    // GET TICKETS OF CUSTOMER
    List<Ticket> getTicketsOfCustomer(Customer customer){
        return customer.getTickets();
    }


    // DELETE CUSTOMER
    void deleteCustomerById(long id){
        customer_repository.deleteById(id);
    }


    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }



}
