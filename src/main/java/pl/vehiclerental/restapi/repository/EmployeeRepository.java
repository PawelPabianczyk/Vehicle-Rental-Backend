package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.models.User;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);

    List<Employee> findAllByIsActive(Boolean isActive);
}
