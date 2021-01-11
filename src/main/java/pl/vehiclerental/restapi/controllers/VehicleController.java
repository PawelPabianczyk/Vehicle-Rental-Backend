package pl.vehiclerental.restapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.InspectionDto;
import pl.vehiclerental.restapi.dtos.InsuranceDto;
import pl.vehiclerental.restapi.dtos.VehicleDto;
import pl.vehiclerental.restapi.models.*;
import pl.vehiclerental.restapi.payload.request.AddVehicleRequest;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.CategoryRepository;
import pl.vehiclerental.restapi.repository.InspectionRepository;
import pl.vehiclerental.restapi.repository.InsuranceRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;
import pl.vehiclerental.restapi.utilities.Converter;

import java.util.List;
import java.util.stream.Collectors;

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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR')")
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<VehicleDto> getAllActiveVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAllByIsActive(true);
        return vehicles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VehicleDto convertToDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new ModelMapper().map(vehicle, VehicleDto.class);
        vehicleDto.setCategory(vehicle.getCategory().getName().name());
        return vehicleDto;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> addVehicle(@RequestBody AddVehicleRequest addVehicleRequest) {

        VehicleDto vehicleDto = addVehicleRequest.getVehicleDto();

        Vehicle vehicle = new Vehicle(
                vehicleDto.getBrand(),
                vehicleDto.getModel(),
                vehicleDto.getYearOfProduction(),
                vehicleDto.getCountry(),
                vehicleDto.getPower(),
                vehicleDto.getPrice(),
                vehicleDto.getDescription(),
                vehicleDto.getPictureUrl()
        );

        String strCategory = vehicleDto.getCategory();
        vehicle.setCategory(Converter.stringsToCategory(categoryRepository, strCategory));

        InsuranceDto insuranceDto = addVehicleRequest.getInsuranceDto();

        Insurance insurance = new Insurance(
                insuranceDto.getDateOfPurchase(),
                insuranceDto.getExpirationDate(),
                insuranceDto.getPrice()
        );
        insurance.setVehicle(vehicle);
        insuranceRepository.save(insurance);

        InspectionDto inspectionDto = addVehicleRequest.getInspectionDto();

        Inspection inspection = new Inspection(
                inspectionDto.getStartDate(),
                inspectionDto.getExpirationDate(),
                inspectionDto.getPrice()
        );
        inspection.setVehicle(vehicle);
        inspectionRepository.save(inspection);

        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(new MessageResponse("Vehicle added successfully!"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> updateVehicle(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId()).get();

        if (vehicleDto.getBrand() != null)
            vehicle.setBrand(vehicleDto.getBrand());

        if (vehicleDto.getModel() != null)
            vehicle.setModel(vehicleDto.getModel());

        if (vehicleDto.getYearOfProduction() != null)
            vehicle.setYearOfProduction(vehicleDto.getYearOfProduction());

        if (vehicleDto.getCountry() != null)
            vehicle.setCountry(vehicleDto.getCountry());

        if (vehicleDto.getPower() != null)
            vehicle.setPower(vehicleDto.getPower());

        if (vehicleDto.getPrice() != null)
            vehicle.setPrice(vehicleDto.getPrice());

        if (vehicleDto.getDescription() != null)
            vehicle.setDescription(vehicleDto.getDescription());

        if (vehicleDto.getPictureUrl() != null)
            vehicle.setPictureUrl(vehicleDto.getPictureUrl());

        if (vehicleDto.getCategory() != null) {
            vehicle.setCategory(Converter.stringsToCategory(categoryRepository, vehicleDto.getCategory()));
        }

        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Vehicle updated successfully");
    }
}
