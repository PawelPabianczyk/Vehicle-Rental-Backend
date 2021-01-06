package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.PersonalInformation;

import java.util.List;

@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {

    Boolean existsByPhone(String phone);

    List<PersonalInformation> findAllByIsActive(Boolean isActive);
}
