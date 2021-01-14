package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomerOrderByDateDesc(Customer customer);

    List<Order> findAllByIsPaid(Boolean isPaid);
}
