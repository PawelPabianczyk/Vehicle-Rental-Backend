package pl.vehiclerental.restapi.controllers;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    @GetMapping("/active")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> getAllActiveInsurances() throws JSONException {
        List<Insurance> insurances = insuranceRepository.findByIsActiveOrderByExpirationDateAsc(true);

        JSONArray jInsurances = new JSONArray();
        JSONObject jInsurance;

        for (Insurance i :
                insurances) {
            jInsurance = new JSONObject();
            jInsurance.put("id", i.getId());
            jInsurance.put("dateOfPurchase", i.getDateOfPurchase());
            jInsurance.put("expirationDate", i.getExpirationDate());
            jInsurance.put("price", i.getPrice());
            jInsurance.put("brand", i.getVehicle().getBrand());
            jInsurance.put("model", i.getVehicle().getModel());
            jInsurance.put("yearOfProduction", i.getVehicle().getYearOfProduction());
            jInsurance.put("country", i.getVehicle().getCountry());
            jInsurances.put(jInsurance);
        }

        return ResponseEntity.ok(jInsurances.toString());
    }
}
