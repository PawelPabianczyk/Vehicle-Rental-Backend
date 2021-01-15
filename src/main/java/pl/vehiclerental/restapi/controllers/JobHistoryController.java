package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.EmployeeDto;
import pl.vehiclerental.restapi.dtos.JobHistoryRecordDto;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.models.JobHistoryRecord;
import pl.vehiclerental.restapi.repository.EmployeeRepository;
import pl.vehiclerental.restapi.repository.JobHistoryRecordRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/jobHistory")
public class JobHistoryController {

    @Autowired
    JobHistoryRecordRepository jobHistoryRecordRepository;

    @Autowired
    EmployeeRepository employeeRepository;

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

    @GetMapping("/details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEmployeeJobHistory(@RequestBody EmployeeDto employeeDto) throws JSONException {
        Employee employee = employeeRepository.findById(employeeDto.getId()).get();
        List<JobHistoryRecord> records = jobHistoryRecordRepository.findByEmployee(employee);
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
