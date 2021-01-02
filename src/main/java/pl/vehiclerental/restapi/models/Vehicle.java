package pl.vehiclerental.restapi.models;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(	name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String brand;

    @NotBlank
    @Size(max = 50)
    private String model;

    private LocalDate yearOfProduction;

    @NotBlank
    @Size(max = 50)
    private String country;

    private Double power;

    private Double dailyCost;

    @NotBlank
    @Size(max = 50)
    private String description;

    @NotBlank
    @Size(max = 2083)
    @URL
    private String url;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="category_id")
    private Category category;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, LocalDate yearOfProduction,
                   String country, Double power, Double dailyCost, String description, String url) {
        this.brand = brand;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.country = country;
        this.power = power;
        this.dailyCost = dailyCost;
        this.description = description;
        this.url = url;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(LocalDate yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(Double dailyCost) {
        this.dailyCost = dailyCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
