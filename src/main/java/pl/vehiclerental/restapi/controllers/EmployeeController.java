package pl.vehiclerental.restapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.EmployeeDto;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.models.Job;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.EmployeeRepository;
import pl.vehiclerental.restapi.repository.JobRepository;
import pl.vehiclerental.restapi.repository.PersonalInformationRepository;
import pl.vehiclerental.restapi.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalInformationRepository personalInformationRepository;

    @Autowired
    JobRepository jobRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee boss = employeeRepository.findById(employeeDto.getBossId()).get();
        Employee employee = new Employee(employeeDto.getBonus(),boss);
        User user = userRepository.findById(employeeDto.getUserId()).get();
        employee.setUser(user);
        Job job = jobRepository.findById(employeeDto.getJobId()).get();
        employee.setJob(job);
        employeeRepository.save(employee);
        return ResponseEntity.ok(new MessageResponse("Employee added successfully!"));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('MANAGER')")
    public List<EmployeeDto> getActiveEmployees() {
        List<Employee> employees = employeeRepository.findAllByIsActive(true);
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new ModelMapper().map(employee, EmployeeDto.class);
        employeeDto.setUserId(employee.getUser().getId());
        employeeDto.setJobId(employee.getJob().getId());
        Employee boss = employee.getBoss();
        if(boss != null){
            employeeDto.setBossId(boss.getId());
        }
        else{
            employeeDto.setBossId(null);
        }
        return employeeDto;
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateEmployee(@RequestBody EmployeeDto employeeDto){
        Employee employee = employeeRepository.findById(employeeDto.getId()).get();
        employee.setActive(false);

        User user = userRepository.findById(employee.getUser().getId()).get();
        user.setActive(false);

        PersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setActive(false);

        userRepository.save(user);
        personalInformationRepository.save(personalInformation);
        employeeRepository.save(employee);
        return ResponseEntity.ok(new MessageResponse("Employee deactivated successfully!"));
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateEmployee(@RequestBody EmployeeDto employeeDto){
        Employee employee = employeeRepository.findById(employeeDto.getId()).get();
        employee.setActive(true);

        User user = userRepository.findById(employee.getUser().getId()).get();
        user.setActive(true);

        PersonalInformation personalInformation = user.getPersonalInformation();
        personalInformation.setActive(true);

        userRepository.save(user);
        personalInformationRepository.save(personalInformation);
        employeeRepository.save(employee);
        return ResponseEntity.ok(new MessageResponse("Employee activated successfully!"));
    }
}
