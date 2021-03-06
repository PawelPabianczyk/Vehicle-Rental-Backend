package pl.vehiclerental.restapi.dtos;

import java.time.LocalDate;

public class RentalDto {
    private Long id;
    private String brand;
    private LocalDate startDate;
    private LocalDate endDate;
    private String model;
    private Double resPrice;
    private Long vehicleId;
    private Long complaintId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getResPrice() {
        return resPrice;
    }

    public void setResPrice(Double resPrice) {
        this.resPrice = resPrice;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    @Override
    public String toString() {
        return "id:"+id+
                "brand:"+brand+
                "model:"+model+
                "startDate:"+startDate+
                "endDate:"+endDate+
                "resPrice:"+resPrice+
                "vehicleId:"+vehicleId+
                "complaintId:"+complaintId;
    }
}
