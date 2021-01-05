package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
