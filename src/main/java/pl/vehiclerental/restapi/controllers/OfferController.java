package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.OfferDto;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.models.Offer;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.EmployeeRepository;
import pl.vehiclerental.restapi.repository.OfferRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/offer")
public class OfferController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    OfferRepository offerRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> addOffer(@RequestBody OfferDto offerDto){
        Employee employee = employeeRepository.findById(offerDto.getEmployeeId()).get();
        Offer offer = new Offer();
        offer.setEmployee(employee);
        offer.setDiscount(offerDto.getDiscount());
        offer.setDescription(offerDto.getDescription());

        Set<Vehicle> vehicles = new HashSet<>();
        for (Long id:
                offerDto.getVehicleIds()) {
            Vehicle vehicle = vehicleRepository.findById(id).get();
            vehicles.add(vehicle);
        }
        offer.setVehicles(vehicles);

        offerRepository.save(offer);
        return ResponseEntity.ok("Offer added successfully");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> getAllOffers() throws JSONException{
        List<Offer> offerList = offerRepository.findAll();
        JSONArray jOffers = new JSONArray();
        JSONObject jOffer;

        for (Offer o :
                offerList) {
            jOffer = new JSONObject();
            jOffer.put("employeeUsername",o.getEmployee().getUser().getUsername());
            jOffer.put("firstName",o.getEmployee().getUser().getPersonalInformation().getFirstName());
            jOffer.put("lastName",o.getEmployee().getUser().getPersonalInformation().getLastName());
            jOffer.put("offerId", o.getId());
            jOffer.put("description", o.getDescription());
            jOffer.put("discount", o.getDiscount());

            Set<Vehicle> vehicles = o.getVehicles();
            JSONArray jVehicles = new JSONArray();
            JSONObject jVehicle;

            for (Vehicle v :
                    vehicles) {
                jVehicle = new JSONObject();
                jVehicle.put("vehicleId", v.getId());
                jVehicle.put("brand", v.getBrand());
                jVehicle.put("model", v.getModel());
                jVehicle.put("price", v.getPrice());
                jVehicles.put(jVehicle);
            }
            jOffer.put("vehicles",jVehicles);
            jOffers.put(jOffer);
        }

        return ResponseEntity.ok(jOffers.toString());
    }
}
