package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vehiclerental.restapi.models.Category;
import pl.vehiclerental.restapi.models.ECategory;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(ECategory name);
}
