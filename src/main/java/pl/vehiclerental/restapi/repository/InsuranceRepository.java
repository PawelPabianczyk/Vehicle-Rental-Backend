package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Insurance;
import pl.vehiclerental.restapi.models.Vehicle;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    List<Insurance> findAllByVehicle(Vehicle vehicle);

    List<Insurance> findByIsActiveOrderByExpirationDateAsc(Boolean isActive);

}
