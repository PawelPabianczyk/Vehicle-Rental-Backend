package pl.vehiclerental.restapi.payload.response;

import pl.vehiclerental.restapi.models.PersonalInformation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private PersonalInformation personalInformation;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, PersonalInformation personalInformation, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.personalInformation = personalInformation;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return personalInformation.getFirstName();
    }

    public void setFirstName(String firstName) {
        this.personalInformation.setFirstName(firstName);
    }

    public String getLastName() {
        return personalInformation.getLastName();
    }

    public void setLastName(String lastName) {
        this.personalInformation.setLastName(lastName);
    }

    public String getAddress() {
        return personalInformation.getAddress();
    }

    public void setAddress(String address) {
        this.personalInformation.setAddress(address);
    }

    public String getCity() {
        return personalInformation.getCity();
    }

    public void setCity(String city) {
        this.personalInformation.setCity(city);
    }

    public String getCountry() {
        return personalInformation.getCountry();
    }

    public void setCountry(String country) {
        this.personalInformation.setCountry(country);
    }

    public String getPhone() {
        return personalInformation.getPhone();
    }

    public void setPhone(String phone) {
        this.personalInformation.setPhone(phone);
    }

    public List<String> getRoles() {
        return roles;
    }
}
