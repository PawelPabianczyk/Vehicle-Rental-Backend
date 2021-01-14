package pl.vehiclerental.restapi.dtos;

import java.util.Set;

public class OfferDto {
    private Long id;
    private Double discount;
    private String description;
    private Long employeeId;
    private Set<Long> vehicleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Set<Long> getVehicleIds() {
        return vehicleIds;
    }

    public void setVehicleIds(Set<Long> vehicleIds) {
        this.vehicleIds = vehicleIds;
    }
}
