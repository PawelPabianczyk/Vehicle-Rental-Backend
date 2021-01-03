package pl.vehiclerental.restapi.payload.request;

import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class AddVehicleRequest {

    @NotBlank
    @Size(max = 50)
    private String brand;

    @NotBlank
    @Size(max = 50)
    private String model;

    private String year;

    @NotBlank
    @Size(max = 50)
    private String country;

    private Double power;

    private Double price;

    @NotBlank
    @Size(max = 50)
    private String description;

    @NotBlank
    @Size(max = 2083)
    @URL
    private String pictureURL;

    private String category;

    private LocalDate inStartDate;
    private LocalDate inExpDate;
    private Double inPrice;

    private LocalDate carInStartDate;
    private LocalDate carInExpDate;
    private Double carInPrice;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getInStartDate() {
        return inStartDate;
    }

    public void setInStartDate(LocalDate inStartDate) {
        this.inStartDate = inStartDate;
    }

    public LocalDate getInExpDate() {
        return inExpDate;
    }

    public void setInExpDate(LocalDate inExpDate) {
        this.inExpDate = inExpDate;
    }

    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }

    public LocalDate getCarInStartDate() {
        return carInStartDate;
    }

    public void setCarInStartDate(LocalDate carInStartDate) {
        this.carInStartDate = carInStartDate;
    }

    public LocalDate getCarInExpDate() {
        return carInExpDate;
    }

    public void setCarInExpDate(LocalDate carInExpDate) {
        this.carInExpDate = carInExpDate;
    }

    public Double getCarInPrice() {
        return carInPrice;
    }

    public void setCarInPrice(Double carInPrice) {
        this.carInPrice = carInPrice;
    }
}
