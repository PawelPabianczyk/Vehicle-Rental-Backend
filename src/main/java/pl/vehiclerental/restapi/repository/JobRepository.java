package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
