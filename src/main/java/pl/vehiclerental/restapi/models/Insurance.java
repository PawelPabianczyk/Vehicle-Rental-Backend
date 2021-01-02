package pl.vehiclerental.restapi.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(	name = "insurances")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfPurchase;

    private LocalDate expirationDate;

    private double price;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

    public Insurance() {
    }

    public Insurance(LocalDate dateOfPurchase, LocalDate expirationDate, double price) {
        this.dateOfPurchase = dateOfPurchase;
        this.expirationDate = expirationDate;
        this.price = price;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}