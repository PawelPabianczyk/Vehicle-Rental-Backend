package pl.vehiclerental.restapi.models;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "vehicles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "brand")
        })
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String brand;

    @NotBlank
    @Size(max = 2083)
    @URL
    private String url;

    public Vehicle() {
    }

    public Vehicle(String brand, String url) {
        this.brand = brand;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getbrand() {
        return brand;
    }

    public void setbrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
