package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
