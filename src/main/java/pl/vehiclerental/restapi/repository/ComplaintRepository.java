package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
