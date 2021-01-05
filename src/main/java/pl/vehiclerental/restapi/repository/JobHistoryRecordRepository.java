package pl.vehiclerental.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vehiclerental.restapi.models.JobHistoryRecord;

@Repository
public interface JobHistoryRecordRepository extends JpaRepository<JobHistoryRecord, Long> {
}
