package pl.vehiclerental.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.InsuranceDto;
import pl.vehiclerental.restapi.models.Insurance;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.InsuranceRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    @Autowired
    InsuranceRepository insuranceRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> addInsurance(@RequestBody InsuranceDto insuranceDto){
        Vehicle vehicle = vehicleRepository.findById(insuranceDto.getVehicleId()).get();

        Insurance insurance = new Insurance();
        insurance.setDateOfPurchase(insuranceDto.getDateOfPurchase());
        insurance.setExpirationDate(insuranceDto.getExpirationDate());
        insurance.setVehicle(vehicle);
        insurance.setActive(true);
        insurance.setPrice(insuranceDto.getPrice());

        List<Insurance> insurances = insuranceRepository.findAllByVehicle(vehicle);

        for (Insurance i :
                insurances) {
            i.setActive(false);
            insuranceRepository.save(i);
        }

        insuranceRepository.save(insurance);

        return ResponseEntity.ok("Insurance added successfully");
    }
}
