package at.ac.fhsalzburg.swd.spring;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentProvider {

    // VARIABLES
    @Autowired
    private PaymentRepository payment_repository;

    // GETTER & SETTER
    // GET ALL PAYMENTS
    public List<Payment> getPayments(){
        return (List<Payment>) payment_repository.findAll();
    }

    // GET NEW PAYMENT
    Payment getNewPayment(Payment payment){
        // Ticket already in list?
        try {
            if (payment_repository.findById(payment.getId()).isEmpty()){
                payment_repository.save(payment);
            }
        }
        catch(Exception e) {
            payment_repository.save(payment);
        }

        payment_repository.save(payment);
        return payment;
    };

    // DELETE PAYMENT
    void deletePaymentById(long id){
        payment_repository.deleteById(id);
    }

    // GET PAYMENT
    Payment getPayment(long id){
        return payment_repository.findById(id);
    };



}
