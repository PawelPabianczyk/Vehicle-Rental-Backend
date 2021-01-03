package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
