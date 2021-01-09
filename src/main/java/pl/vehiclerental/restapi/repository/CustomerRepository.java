package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);

    List<Customer> findAllByIsActive(Boolean isActive);

    List<Customer> findAllByIsVerified(Boolean isVerified);


}
