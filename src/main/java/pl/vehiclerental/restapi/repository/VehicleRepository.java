package pl.vehiclerental.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<User> findByBrand(String brand);

    Boolean existsByBrand(String brand);
}
