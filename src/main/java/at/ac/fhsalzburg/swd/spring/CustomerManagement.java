package at.ac.fhsalzburg.swd.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManagement {

    @Autowired
    private CustomerRepository customer_repository;


    // ADD CUSTOMER
    List <Customer> addCustomer(Customer customer){
        // Customer already in list?
        try {
            if (customer_repository.findByLastName(customer.getLastName()).isEmpty()){
                customer_repository.save(customer);
                return customer_repository.findByLastName(customer.getLastName());
            }
        } catch(Exception e) { }
        return customer_repository.findByLastName(customer.getLastName());
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

    // GET CUSTOMER BY NAME - Inside the class diagram referred to as "getCustomer(licence String)"
    Customer getCustomer(String firstName, String lastName){

        List<Customer> customersByLastName = customer_repository.findByLastName(lastName);

        // No such user in list?
        if (customersByLastName.isEmpty()){
            return null;
        } else {
            for (Customer customerByLastName : customersByLastName) {
                if (customerByLastName.getFirstName().equals(firstName)) {
                    return customerByLastName;
                }
            }
        }

        return null;
    }


   // DELETE CUSTOMER
    void deleteCustomerById(long id){
        customer_repository.deleteById(id);
    }


}
