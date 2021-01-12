package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.InspectionDto;
import pl.vehiclerental.restapi.models.Inspection;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.InspectionRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/inspection")
public class InspectionController {

    @Autowired
    InspectionRepository inspectionRepository;

    @Autowired
    VehicleRepository vehicleRepository;



    @GetMapping("/active")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> getAllActiveInspections() throws JSONException {
        List<Inspection> inspections = inspectionRepository.findByIsActiveOrderByExpirationDateAsc(true);

        JSONArray jInspections = new JSONArray();
        JSONObject jInspection;

        for (Inspection i :
                inspections) {
            jInspection = new JSONObject();
            jInspection.put("id", i.getId());
            jInspection.put("startDate", i.getStartDate());
            jInspection.put("expirationDate", i.getExpirationDate());
            jInspection.put("price", i.getPrice());
            jInspection.put("brand", i.getVehicle().getBrand());
            jInspection.put("model", i.getVehicle().getModel());
            jInspection.put("yearOfProduction", i.getVehicle().getYearOfProduction());
            jInspection.put("country", i.getVehicle().getCountry());
            jInspections.put(jInspection);
        }

        return ResponseEntity.ok(jInspections.toString());
    }
}
