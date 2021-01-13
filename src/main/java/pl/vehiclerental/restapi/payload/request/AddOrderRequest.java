package pl.vehiclerental.restapi.payload.request;

import pl.vehiclerental.restapi.dtos.RentalDto;

import java.util.List;

public class AddOrderRequest {

    private Long customerId;

    private String comments;

    private List<RentalDto> rentals;

    private String payment;

    public List<RentalDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDto> rentals) {
        this.rentals = rentals;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
