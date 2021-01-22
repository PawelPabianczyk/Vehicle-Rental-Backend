package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import pl.vehiclerental.restapi.dtos.InsuranceDto;
import pl.vehiclerental.restapi.models.Insurance;
import pl.vehiclerental.restapi.models.Vehicle;
import pl.vehiclerental.restapi.repository.InsuranceRepository;
import pl.vehiclerental.restapi.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class InsuranceControllerTest {

    @Autowired
    InsuranceController insuranceController;

    @Autowired
    InsuranceRepository insuranceRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(insuranceController).isNotNull();
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void addInsurance() {
        InsuranceDto insuranceDto = new InsuranceDto();
        insuranceDto.setVehicleId(1L);
        insuranceDto.setDateOfPurchase(LocalDate.now());
        insuranceDto.setExpirationDate(LocalDate.now().plusYears(2));
        insuranceDto.setPrice(200.);
        insuranceController.addInsurance(insuranceDto);
        Vehicle vehicleDb = vehicleRepository.findById(1L).get();
        Insurance insuranceDb = insuranceRepository.findByVehicleAndIsActive(vehicleDb, true).get();
        assertThat(insuranceDb).isNotNull();
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void getAllActiveInsurances() throws JSONException {
        String jInsurances = insuranceController.getAllActiveInsurances().toString();
        String [] jObjects = jInsurances.split("vehicleId");
        List<Insurance> insurancesDb = insuranceRepository.findByIsActiveOrderByExpirationDateAsc(true);
        assertThat(insurancesDb.size()).isEqualTo(jObjects.length-1);

    }
}