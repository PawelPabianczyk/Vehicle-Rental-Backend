package pl.vehiclerental.restapi.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(	name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private double cost;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

    @OneToOne(mappedBy = "rental", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Complaint complaint;

    public Rental() {
    }

    public Rental(LocalDate startDate, LocalDate endDate, double cost) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
