package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}