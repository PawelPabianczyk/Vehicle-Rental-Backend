package pl.vehiclerental.restapi.payload.request;

import pl.vehiclerental.restapi.dtos.InspectionDto;
import pl.vehiclerental.restapi.dtos.InsuranceDto;
import pl.vehiclerental.restapi.dtos.VehicleDto;

public class AddVehicleRequest {

    private VehicleDto vehicleDto;

    private InsuranceDto insuranceDto;

    private InspectionDto inspectionDto;

    public VehicleDto getVehicleDto() {
        return vehicleDto;
    }

    public void setVehicleDto(VehicleDto vehicleDto) {
        this.vehicleDto = vehicleDto;
    }

    public InsuranceDto getInsuranceDto() {
        return insuranceDto;
    }

    public void setInsuranceDto(InsuranceDto insuranceDto) {
        this.insuranceDto = insuranceDto;
    }

    public InspectionDto getInspectionDto() {
        return inspectionDto;
    }

    public void setInspectionDto(InspectionDto inspectionDto) {
        this.inspectionDto = inspectionDto;
    }
}
