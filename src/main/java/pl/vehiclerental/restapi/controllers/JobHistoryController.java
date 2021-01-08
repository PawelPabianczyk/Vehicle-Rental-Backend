package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vehiclerental.restapi.models.JobHistoryRecord;
import pl.vehiclerental.restapi.repository.JobHistoryRecordRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/jobHistory")
public class JobHistoryController {

    @Autowired
    JobHistoryRecordRepository jobHistoryRecordRepository;


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllJobHistory() throws JSONException {
        List<JobHistoryRecord> records = jobHistoryRecordRepository.findAll();
        JSONArray jRecords = new JSONArray();
        JSONObject jRecord;
        for (JobHistoryRecord r :
                records) {
            jRecord = new JSONObject();
            jRecord.put("id", r.getId());
            jRecord.put("startDate", r.getStartDate());
            jRecord.put("endDate", r.getEndDate());
            jRecord.put("employeeId", r.getEmployee().getId());
            jRecords.put(jRecord);
        }

        return ResponseEntity.ok(jRecords.toString());
    }
}
