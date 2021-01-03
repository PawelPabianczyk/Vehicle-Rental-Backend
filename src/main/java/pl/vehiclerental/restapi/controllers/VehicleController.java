package pl.vehiclerental.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.models.*;
import pl.vehiclerental.restapi.payload.request.AddVehicleRequest;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    InsuranceRepository insuranceRepository;

    @Autowired
    InspectionRepository inspectionRepository;

    @GetMapping("/all")
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody AddVehicleRequest addVehicleRequest) {

        int year = Integer.parseInt(addVehicleRequest.getYear());

        Vehicle vehicle = new Vehicle(
                addVehicleRequest.getBrand(),
                addVehicleRequest.getModel(),
                LocalDate.of(year,1,1),
                addVehicleRequest.getCountry(),
                addVehicleRequest.getPower(),
                addVehicleRequest.getPrice(),
                addVehicleRequest.getDescription(),
                addVehicleRequest.getPictureURL()
        );

        String strCategory = addVehicleRequest.getCategory();

        switch (strCategory) {
            case "Sedan":
                Category sedanCategory = categoryRepository.findByName(ECategory.SEDAN)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                vehicle.setCategory(sedanCategory);

                break;
            case "Coupe":
                Category coupeCategory = categoryRepository.findByName(ECategory.COUPE)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                vehicle.setCategory(coupeCategory);

                break;

            case "Sports":
                Category sportsCategory = categoryRepository.findByName(ECategory.SPORTS)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                vehicle.setCategory(sportsCategory);

                break;
            case "Hatchback":
                Category hatchbackCategory = categoryRepository.findByName(ECategory.HATCHBACK)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                vehicle.setCategory(hatchbackCategory);

                break;
                case "SUV":
                Category suvCategory = categoryRepository.findByName(ECategory.SUV)
                        .orElseThrow(() -> new RuntimeException("Error: Category is not found."));
                vehicle.setCategory(suvCategory);

                break;
            default:
                throw new RuntimeException("Error: Category is not found.");
        }

        Insurance insurance = new Insurance(
                addVehicleRequest.getInStartDate(),
                addVehicleRequest.getInExpDate(),
                addVehicleRequest.getPrice()
        );
        insurance.setVehicle(vehicle);
        insuranceRepository.save(insurance);

        Inspection inspection = new Inspection(
                addVehicleRequest.getCarInStartDate(),
                addVehicleRequest.getCarInExpDate(),
                addVehicleRequest.getCarInPrice()
        );
        inspection.setVehicle(vehicle);
        inspectionRepository.save(inspection);

        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(new MessageResponse("Vehicle added successfully!"));
    }
}
