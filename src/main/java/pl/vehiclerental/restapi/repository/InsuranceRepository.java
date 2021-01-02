package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vehiclerental.restapi.models.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
