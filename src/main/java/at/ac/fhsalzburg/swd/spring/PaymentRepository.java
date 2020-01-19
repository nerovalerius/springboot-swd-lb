package at.ac.fhsalzburg.swd.spring;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	@Transactional(timeout = 10)
	Payment findById(long id);

	//@Transactional(timeout = 10)
	//@Override
	//Customer save(Customer customer);
	
}
