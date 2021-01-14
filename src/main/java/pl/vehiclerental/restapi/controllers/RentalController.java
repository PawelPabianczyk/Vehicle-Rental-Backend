package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.VehicleDto;
import pl.vehiclerental.restapi.models.Rental;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.OrderRepository;
import pl.vehiclerental.restapi.repository.RentalRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rental")
public class RentalController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/vehicleRentals")
    public ResponseEntity<?> getAllVehicleRentals(@RequestBody VehicleDto vehicleDto) throws JSONException {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId()).get();
        List<Rental> rentals = rentalRepository.findAllByVehicle(vehicle);

        JSONArray jRentals = new JSONArray();
        JSONObject jRental;

        for (Rental r :
                rentals) {
            jRental = new JSONObject();
            jRental.put("startDate", r.getStartDate());
            jRental.put("endDate", r.getEndDate());
            jRentals.put(jRental);
        }
        return ResponseEntity.ok(jRentals.toString());
    }

    @PostMapping("/vehicleRentalsDetails")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR')")
    public ResponseEntity<?> getAllVehicleRentalsDetails(@RequestBody VehicleDto vehicleDto) throws JSONException {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId()).get();
        List<Rental> rentals = rentalRepository.findAllByVehicleOrderByStartDateDesc(vehicle);

        JSONObject response = new JSONObject();
        JSONArray jRentals = new JSONArray();
        JSONObject jRental;

        for (Rental r :
                rentals) {
            jRental = new JSONObject();
            jRental.put("id", r.getId());
            jRental.put("cost", r.getCost());
            jRental.put("startDate", r.getStartDate());
            jRental.put("endDate", r.getEndDate());
            jRentals.put(jRental);
        }

        response.put("rentals",jRentals);
        return ResponseEntity.ok(response.toString());
    }
}
