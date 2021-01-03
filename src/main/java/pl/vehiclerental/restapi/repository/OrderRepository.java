package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
