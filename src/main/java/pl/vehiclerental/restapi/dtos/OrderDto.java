package pl.vehiclerental.restapi.dtos;

import java.time.LocalDate;
import java.util.Set;

public class OrderDto {
    private Long id;
    private String comments;
    private LocalDate date;
    private double cost;
    private Boolean isPaid;
    private Set<RentalDto> rentals;
    private PaymentDto payment;
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Set<RentalDto> getRentals() {
        return rentals;
    }

    public void setRentals(Set<RentalDto> rentals) {
        this.rentals = rentals;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
