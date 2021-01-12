package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.CustomerDto;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.PersonalInformationRepository;
import pl.vehiclerental.restapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalInformationRepository personalInformationRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CustomerDto convertToDto(Customer customer){
        CustomerDto customerDto = new ModelMapper().map(customer, CustomerDto.class);
        customerDto.setUserId(customer.getUser().getId());
        if (customer.getVerified())
            customerDto.setStatus("verified");
        else
            customerDto.setStatus("unverified");
        return customerDto;
    }

    @GetMapping("/unverified")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR')")
    public ResponseEntity<?> getAllUnverifiedCustomers() throws JSONException {
        List<Customer> customers = customerRepository.findAllByIsVerifiedAndDrivingLicenseNumberIsNotNull(false);

        JSONArray jCustomers = new JSONArray();
        JSONObject jCustomer;
        for (Customer c :
                customers) {
            jCustomer = new JSONObject();
            jCustomer.put("id", c.getId());
            jCustomer.put("drivingLicenseNumber", c.getDrivingLicenseNumber());
            jCustomer.put("firstName", c.getUser().getPersonalInformation().getFirstName());
            jCustomer.put("lastName", c.getUser().getPersonalInformation().getLastName());
            jCustomer.put("address", c.getUser().getPersonalInformation().getAddress());
            jCustomer.put("city", c.getUser().getPersonalInformation().getCity());
            jCustomer.put("country", c.getUser().getPersonalInformation().getCountry());
            jCustomers.put(jCustomer);
        }
        return ResponseEntity.ok(jCustomers.toString());
    }

    @GetMapping("/verified")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR')")
    public ResponseEntity<?> getAllVerifiedCustomers() throws JSONException {
        List<Customer> customers = customerRepository.findAllByIsVerified(true);

        JSONArray jCustomers = new JSONArray();
        JSONObject jCustomer;
        for (Customer c :
                customers) {
            jCustomer = new JSONObject();
            jCustomer.put("id", c.getId());
            jCustomer.put("licenseNumber", c.getDrivingLicenseNumber());
            jCustomer.put("firstName", c.getUser().getPersonalInformation().getFirstName());
            jCustomer.put("lastName", c.getUser().getPersonalInformation().getLastName());
            jCustomer.put("address", c.getUser().getPersonalInformation().getAddress());
            jCustomer.put("city", c.getUser().getPersonalInformation().getCity());
            jCustomer.put("country", c.getUser().getPersonalInformation().getCountry());
            jCustomers.put(jCustomer);
        }
        return ResponseEntity.ok(jCustomers.toString());
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = customerRepository.findById(customerDto.getId()).get();
        customer.setActive(false);

        User user = userRepository.findById(customer.getUser().getId()).get();
        user.setActive(false);

        PersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setActive(false);

        userRepository.save(user);
        personalInformationRepository.save(personalInformation);
        customerRepository.save(customer);
        return ResponseEntity.ok(new MessageResponse("Customer deactivated successfully!"));
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = customerRepository.findById(customerDto.getId()).get();
        customer.setActive(true);

        User user = userRepository.findById(customer.getUser().getId()).get();
        user.setActive(true);

        PersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setActive(true);

        userRepository.save(user);
        personalInformationRepository.save(personalInformation);
        customerRepository.save(customer);
        return ResponseEntity.ok(new MessageResponse("Customer activated successfully!"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR') or hasRole('USER')")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto){
        User user = userRepository.findById(customerDto.getUserId()).get();

        Customer customer = customerRepository.findByUser(user).get();

        if(customerDto.getDrivingLicenseNumber() != null){
            customer.setDrivingLicenseNumber(customerDto.getDrivingLicenseNumber());
            customer.setVerified(false);
        }

        if(customerDto.getStatus() != null){
            if(customerDto.getStatus().equals("verified")){
                customer.setVerified(true);
            }
            else
                customer.setVerified(false);
        }
        customerRepository.save(customer);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @PostMapping("/license")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR') or hasRole('USER')")
    public ResponseEntity<?> getCustomerLicense(@RequestBody CustomerDto customerDto) throws JSONException {
        Customer customer = customerRepository.findById(customerDto.getId()).get();
        JSONObject jCustomer = new JSONObject();
        jCustomer.put("idCustomer", customer.getDrivingLicenseNumber());
        return ResponseEntity.ok(jCustomer.toString());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR') or hasRole('USER')")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto){
        User user = userRepository.findById(customerDto.getUserId()).get();

        Customer customer = new Customer();
        customer.setDrivingLicenseNumber(customerDto.getDrivingLicenseNumber());
        customer.setVerified(false);
        customer.setUser(user);
        customer.setActive(true);

        customerRepository.save(customer);
        return ResponseEntity.ok("Customer added successfully");
    }
}
