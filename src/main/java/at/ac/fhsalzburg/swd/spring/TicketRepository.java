package at.ac.fhsalzburg.swd.spring;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface TicketRepository extends CrudRepository<Ticket, Long> {

	@Transactional(timeout = 10)
	Ticket findById(long id);

	/*
	@Transactional(timeout = 10)
	Ticket findByTicket(Ticket ticket);
	*/

	//@Transactional(timeout = 10)
	//@Override
	//Customer save(Customer customer);
	
}

