package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.ComplaintDto;
import pl.vehiclerental.restapi.dtos.RentalDto;
import pl.vehiclerental.restapi.models.Complaint;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.Rental;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.ComplaintRepository;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.RentalRepository;
import pl.vehiclerental.restapi.utilities.Converter;

import javax.persistence.EntityManager;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManager em;

    @GetMapping("/all")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> getAllComplaints() throws JSONException {
        List<Complaint> complaints = complaintRepository.findAll();
        JSONArray jComplaints = new JSONArray();
        JSONObject jComplaint;

        for (Complaint c :
                complaints) {
            jComplaint = new JSONObject();
            jComplaint.put("description",c.getDescription());
            RentalDto r = Converter.convertRentalToRentalDto(c.getRental());
            jComplaint.put("id",r.getId());
            jComplaint.put("brand",r.getBrand());
            jComplaint.put("model",r.getModel());
            jComplaint.put("startDate",r.getStartDate());
            jComplaint.put("endDate",r.getEndDate());
            jComplaint.put("resPrice",r.getResPrice());
            jComplaint.put("vehicleId",r.getVehicleId());

            Object customerId = em.createNativeQuery("SELECT o.customer_id FROM orders o " +
                    "JOIN order_rentals orr ON o.id=orr.order_id " +
                    "JOIN rentals r ON orr.rental_id=r.id WHERE r.id=" + c.getRental().getId())
                    .getSingleResult();



            Customer customer = customerRepository.findById(Long.parseLong(customerId.toString())).get();
            jComplaint.put("firstName", customer.getUser().getPersonalInformation().getFirstName());
            jComplaint.put("lastName", customer.getUser().getPersonalInformation().getLastName());
            jComplaint.put("email", customer.getUser().getEmail());

            jComplaints.put(jComplaint);
        }

        return ResponseEntity.ok(jComplaints.toString());
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> getAllActiveComplaints() throws JSONException {
        List<Complaint> complaints = complaintRepository.findAllByIsActive(true);
        JSONArray jComplaints = new JSONArray();
        JSONObject jComplaint;

        for (Complaint c :
                complaints) {
            jComplaint = new JSONObject();
            jComplaint.put("description",c.getDescription());
            RentalDto r = Converter.convertRentalToRentalDto(c.getRental());
            jComplaint.put("id",r.getId());
            jComplaint.put("brand",r.getBrand());
            jComplaint.put("model",r.getModel());
            jComplaint.put("startDate",r.getStartDate());
            jComplaint.put("endDate",r.getEndDate());
            jComplaint.put("resPrice",r.getResPrice());
            jComplaint.put("vehicleId",r.getVehicleId());

            Object customerId = em.createNativeQuery("SELECT o.customer_id FROM orders o " +
                    "JOIN order_rentals orr ON o.id=orr.order_id " +
                    "JOIN rentals r ON orr.rental_id=r.id WHERE r.id=" + c.getRental().getId())
                    .getSingleResult();



            Customer customer = customerRepository.findById(Long.parseLong(customerId.toString())).get();
            jComplaint.put("firstName", customer.getUser().getPersonalInformation().getFirstName());
            jComplaint.put("lastName", customer.getUser().getPersonalInformation().getLastName());
            jComplaint.put("email", customer.getUser().getEmail());

            jComplaints.put(jComplaint);
        }

        return ResponseEntity.ok(jComplaints.toString());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('REGULAR') or hasRole('USER')")
    public ResponseEntity<?> addComplaint(@RequestBody ComplaintDto complaintDto){
        Rental rental = rentalRepository.findById(complaintDto.getRentalId()).get();

        Complaint complaint = new Complaint();
        complaint.setDescription(complaintDto.getDescription());
        complaint.setRental(rental);
        complaintRepository.save(complaint);
        rental.setComplaint(complaint);
        rentalRepository.save(rental);
        return ResponseEntity.ok("Complaint added successfully");
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> deactivateComplaint(@RequestBody ComplaintDto complaintDto){
        Complaint complaint = complaintRepository.findById(complaintDto.getId()).get();
        complaint.setActive(false);
        complaintRepository.save(complaint);
        return ResponseEntity.ok(new MessageResponse("Complaint deactivated successfully!"));
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> activateComplaint(@RequestBody ComplaintDto complaintDto){
        Complaint complaint = complaintRepository.findById(complaintDto.getId()).get();
        complaint.setActive(true);
        complaintRepository.save(complaint);
        return ResponseEntity.ok(new MessageResponse("Complaint activated successfully!"));
    }
}
