package pl.vehiclerental.restapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.CustomerDto;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.request.UpdateUserRequest;
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
        return customerDto;
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
            customer.setVerificationStatus("unverified");
        }

        if(customerDto.getVerificationStatus() != null){
            customer.setVerificationStatus(customerDto.getVerificationStatus());
        }

        customerRepository.save(customer);

        return ResponseEntity.ok("Customer updated successfully");
    }
}
