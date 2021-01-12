package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Inspection;
import pl.vehiclerental.restapi.models.Vehicle;

import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    List<Inspection> findAllByVehicle(Vehicle vehicle);

    List<Inspection> findByIsActiveOrderByExpirationDateAsc(Boolean isActive);
}
