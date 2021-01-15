package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.EmployeeDto;
import pl.vehiclerental.restapi.models.*;
import pl.vehiclerental.restapi.payload.request.UpdateEmployeeRequest;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.*;
import pl.vehiclerental.restapi.utilities.Converter;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee boss = employeeRepository.findById(employeeDto.getBossId()).get();
        Employee employee = new Employee(employeeDto.getBonus(),boss);
        User user = userRepository.findById(employeeDto.getUserId()).get();
        employee.setUser(user);
        Job job = jobRepository.findByTitle(employeeDto.getJobTitle()).get();
        employee.setJob(job);
        employeeRepository.save(employee);
        return ResponseEntity.ok(new MessageResponse("Employee added successfully!"));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getActiveEmployees() throws JSONException {
        List<Employee> employees = employeeRepository.findAllByIsActive(true);
        JSONArray jEmployees = new JSONArray();
        JSONObject jEmployee;

        for (Employee e :
                employees) {
            jEmployee = new JSONObject();
            jEmployee.put("employeeId", e.getId());
            jEmployee.put("bonus", e.getBonus());

            if(e.getBoss() != null){
                Employee boss = employeeRepository.findById(e.getBoss().getId()).get();
                jEmployee.put("bossUsername", boss.getUser().getUsername());
            }
            else{
                jEmployee.put("bossUsername", null);
            }

            jEmployee.put("userId", e.getUser().getId());
            jEmployee.put("jobTitle", e.getJob().getTitle());
            jEmployee.put("salary", e.getJob().getSalary());

            jEmployee.put("username", e.getUser().getUsername());
            jEmployee.put("email", e.getUser().getEmail());
            jEmployee.put("firstName", e.getUser().getPersonalInformation().getFirstName());
            jEmployee.put("lastName", e.getUser().getPersonalInformation().getLastName());

            Set<String> roles = new HashSet<>();

            for (Role role :
                    e.getUser().getRoles()) {
                roles.add(role.getName().name());
            }
            jEmployee.put("roles", roles);

            jEmployees.put(jEmployee);
        }

        return ResponseEntity.ok(jEmployees.toString());
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getInactiveEmployees() throws JSONException {
        List<Employee> employees = employeeRepository.findAllByIsActive(false);
        JSONArray jEmployees = new JSONArray();
        JSONObject jEmployee;

        for (Employee e :
                employees) {
            jEmployee = new JSONObject();
            jEmployee.put("employeeId", e.getId());
            jEmployee.put("bonus", e.getBonus());

            if(e.getBoss() != null){
                Employee boss = employeeRepository.findById(e.getBoss().getId()).get();
                jEmployee.put("bossUsername", boss.getUser().getUsername());
            }
            else{
                jEmployee.put("bossUsername", null);
            }

            jEmployee.put("userId", e.getUser().getId());
            jEmployee.put("jobTitle", e.getJob().getTitle());
            jEmployee.put("salary", e.getJob().getSalary());

            jEmployee.put("username", e.getUser().getUsername());
            jEmployee.put("email", e.getUser().getEmail());
            jEmployee.put("firstName", e.getUser().getPersonalInformation().getFirstName());
            jEmployee.put("lastName", e.getUser().getPersonalInformation().getLastName());

            Set<String> roles = new HashSet<>();

            for (Role role :
                    e.getUser().getRoles()) {
                roles.add(role.getName().name());
            }
            jEmployee.put("roles", roles);

            jEmployees.put(jEmployee);
        }

        return ResponseEntity.ok(jEmployees.toString());
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

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeRequest request){
        User user = userRepository.findById(request.getUserId()).get();

        if (request.getUsername() != null)
            user.setUsername(request.getUsername());

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        if(request.getFirstName() != null)
            user.getPersonalInformation().setFirstName(request.getFirstName());

        if(request.getLastName() != null)
            user.getPersonalInformation().setLastName(request.getLastName());

        if (request.getJobTitle() != null){
            Job job = jobRepository.findByTitle(request.getJobTitle()).get();
            user.getEmployee().setJob(job);
        }

        if (request.getRoles() != null){
            user.setRoles(Converter.stringsToRoles(roleRepository, request.getRoles()));
        }

        if (request.getBonus() != null)
            user.getEmployee().setBonus(request.getBonus());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Employee updated successfully!"));
    }
}
