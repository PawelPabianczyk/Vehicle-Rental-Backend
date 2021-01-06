package pl.vehiclerental.restapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vehiclerental.restapi.dtos.CustomerDto;
import pl.vehiclerental.restapi.dtos.EmployeeDto;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.EmployeeRepository;
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE')")
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
}
