package pl.vehiclerental.restapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Rental;
import pl.vehiclerental.restapi.models.Vehicle;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByVehicle(Vehicle vehicle);
}
