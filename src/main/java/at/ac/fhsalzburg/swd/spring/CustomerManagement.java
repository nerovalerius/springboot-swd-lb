package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerManagement {

    @Autowired
    private CustomerRepository customer_repository;

    String status;
    Customer currentCustomer;

    // ADD CUSTOMER
    void addCustomer(Customer customer){
        // Customer already in list?
        try {
            if (customer_repository.findByLastName(customer.getLastName()).isEmpty()){
                customer_repository.save(customer);
            }
        }
        catch(Exception e) {
            customer_repository.save(customer);
        }


    };

    // GET CUSTOMER BY ID
    Customer getCustomer(long id){
        return customer_repository.findById(id);
    };

    // GET CUSTOMER BY CUSTOMER
    List<Customer> getCustomer(Customer customer){
        try {
            return customer_repository.findByLastName(customer.getLastName());
        }
        catch(Exception e) {
            return null;
        }
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

    // SET STATUS MESSAGE
    public String getStatus() {
        return status;
    }

    // GET STATUS MESSAGE
    public void setStatus(String status) {
        this.status = status;
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
