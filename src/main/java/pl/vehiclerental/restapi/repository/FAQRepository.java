package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.FAQ;

import java.util.List;
import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findAllByIsActive(Boolean isActive);

    Optional<FAQ> findByQuestionAndAnswer(String question, String answer);
}
