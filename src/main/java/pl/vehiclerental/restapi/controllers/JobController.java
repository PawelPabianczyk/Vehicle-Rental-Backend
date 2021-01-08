package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.CustomerDto;
import pl.vehiclerental.restapi.dtos.JobDto;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.Job;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.JobRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobRepository jobRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getJobs() throws JSONException {
        List<Job> jobs = jobRepository.findAll();

        JSONArray jJobs = new JSONArray();
        JSONObject jJob;
        for (Job j :
                jobs) {
            jJob = new JSONObject();
            jJob.put("id", j.getId());
            jJob.put("title", j.getTitle());
            jJob.put("salary", j.getSalary());

            jJobs.put(jJob);
        }
        return ResponseEntity.ok(jJobs.toString());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addJob(@RequestBody JobDto jobDto){
        Job job = new Job();
        job.setActive(true);
        job.setTitle(jobDto.getTitle());
        job.setSalary(jobDto.getSalary());

        jobRepository.save(job);
        return ResponseEntity.ok(new MessageResponse("Job added successfully!"));
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateJob(@RequestBody Long id){
        Job job = jobRepository.findById(id).get();
        job.setActive(true);
        jobRepository.save(job);
        return ResponseEntity.ok(new MessageResponse("Job activated successfully!"));
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateJob(@RequestBody Long id){
        Job job = jobRepository.findById(id).get();
        job.setActive(false);
        jobRepository.save(job);
        return ResponseEntity.ok(new MessageResponse("Job deactivated successfully!"));
    }
}
