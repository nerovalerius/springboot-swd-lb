package at.ac.fhsalzburg.swd.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@ExtendWith(SpringExtension.class)   // we need spring boot to be running in order to test
@DataJpaTest                          // sets up everything needed for JPA tests (in-memory-db, hibernate, ...)
public class PersistenceLayerTest {
    @Autowired
    private TestEntityManager entityManager;    // TestEntity Manager provides a subset of Entity Manager methods that are useful for
                                                // tests as well as helper methods for common testing tasks such as persist or find.
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {   // Best Practice: structure your code given/when/then// given
        Customer customer = new Customer("Max", "Mustermann");
        entityManager.persist(customer);
        entityManager.flush();
        List<Customer> given = new ArrayList<Customer>();
        given.add(customer);
        // when
        List<Customer> found = customerRepository.findByLastName(customer.getLastName());
        // then
        assertIterableEquals(given, found);
    }
}
