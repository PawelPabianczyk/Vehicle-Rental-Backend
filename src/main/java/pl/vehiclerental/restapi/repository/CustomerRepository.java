package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
