package pl.vehiclerental.restapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
