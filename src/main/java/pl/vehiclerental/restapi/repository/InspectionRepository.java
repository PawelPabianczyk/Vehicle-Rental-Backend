package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Inspection;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {
}
