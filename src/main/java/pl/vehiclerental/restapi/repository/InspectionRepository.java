package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vehiclerental.restapi.models.Inspection;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
}
