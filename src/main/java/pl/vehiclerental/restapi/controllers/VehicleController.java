package pl.vehiclerental.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.VehicleRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping("/all")
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }

}
