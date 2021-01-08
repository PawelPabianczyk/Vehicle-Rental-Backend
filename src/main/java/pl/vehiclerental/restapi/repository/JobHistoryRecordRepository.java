package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.models.JobHistoryRecord;
import pl.vehiclerental.restapi.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobHistoryRecordRepository extends JpaRepository<JobHistoryRecord, Long> {
    List<JobHistoryRecord> findByEmployee(Employee employee);

}
