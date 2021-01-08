package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.JobHistoryRecordDto;
import pl.vehiclerental.restapi.dtos.UserDto;
import pl.vehiclerental.restapi.models.JobHistoryRecord;
import pl.vehiclerental.restapi.models.Role;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.request.UpdateUserRequest;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.*;
import pl.vehiclerental.restapi.utilities.Converter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalInformationRepository personalInformationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JobHistoryRecordRepository jobHistoryRecordRepository;

    @PostMapping("/logOut")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('REGULAR') or hasRole('USER')")
    public ResponseEntity<?> logOut(@RequestBody JobHistoryRecordDto record) {
        User user = userRepository.findById(record.getUserId()).get();
        if (user.getEmployee() != null) {
            JobHistoryRecord jobHistoryRecord = new JobHistoryRecord();
            jobHistoryRecord.setStartDate(record.getStartDate());
            jobHistoryRecord.setEndDate(LocalDate.now());
            jobHistoryRecord.setEmployee(user.getEmployee());
            jobHistoryRecordRepository.save(jobHistoryRecord);
            return ResponseEntity.ok(new MessageResponse("Job history record added successfully!"));
        }


        System.out.println(jobHistoryRecordRepository.findById(1L).get().getEndDate());
        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() throws JSONException {
        List<User> users = userRepository.findAll();

        JSONArray jUsers = new JSONArray();
        JSONObject jUser;
        for (User u :
                users) {
            jUser = new JSONObject();
            jUser.put("id", u.getId());
            jUser.put("username", u.getUsername());
            jUser.put("email", u.getEmail());
            jUser.put("isActive", u.getActive());

            Set<String> roles = new HashSet<>();

            for (Role role :
                    u.getRoles()) {
                roles.add(role.getName().name());
            }

            jUser.put("roles", roles);
            jUser.put("address", u.getPersonalInformation().getAddress());
            jUser.put("birthdate", u.getPersonalInformation().getBirthdate());
            jUser.put("city", u.getPersonalInformation().getCity());
            jUser.put("country", u.getPersonalInformation().getCountry());
            jUser.put("firstName", u.getPersonalInformation().getFirstName());
            jUser.put("lastName", u.getPersonalInformation().getLastName());
            jUser.put("phone", u.getPersonalInformation().getPhone());

            jUsers.put(jUser);
        }

        return ResponseEntity.ok(jUsers.toString());
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllActiveUsers() throws JSONException {
        List<User> users = userRepository.findAllByIsActive(true);

        JSONArray jUsers = new JSONArray();
        JSONObject jUser;
        for (User u :
                users) {
            jUser = new JSONObject();
            jUser.put("id", u.getId());
            jUser.put("username", u.getUsername());
            jUser.put("email", u.getEmail());
            jUser.put("isActive", u.getActive());

            Set<String> roles = new HashSet<>();

            for (Role role :
                    u.getRoles()) {
                roles.add(role.getName().name());
            }

            jUser.put("roles", roles);
            jUser.put("address", u.getPersonalInformation().getAddress());
            jUser.put("birthdate", u.getPersonalInformation().getBirthdate());
            jUser.put("city", u.getPersonalInformation().getCity());
            jUser.put("country", u.getPersonalInformation().getCountry());
            jUser.put("firstName", u.getPersonalInformation().getFirstName());
            jUser.put("lastName", u.getPersonalInformation().getLastName());
            jUser.put("phone", u.getPersonalInformation().getPhone());

            jUsers.put(jUser);
        }

        return ResponseEntity.ok(jUsers.toString());
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateUser(@RequestBody UserDto userDto){
        User user = userRepository.findById(userDto.getId()).get();
        user.setActive(false);

        if(user.getEmployee() != null){
            user.getEmployee().setActive(false);
        }

        if(user.getCustomer() != null){
            user.getCustomer().setActive(false);
        }

        user.getPersonalInformation().setActive(false);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User deactivated successfully!"));
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateUser(@RequestBody UserDto userDto){
        User user = userRepository.findById(userDto.getId()).get();
        user.setActive(true);

        if(user.getEmployee() != null){
            user.getEmployee().setActive(true);
        }

        if(user.getCustomer() != null){
            user.getCustomer().setActive(true);
        }

        user.getPersonalInformation().setActive(true);

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User activated successfully!"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        User user = userRepository.findById(request.getId()).get();

        if (request.getUsername() != null)
            user.setUsername(request.getUsername());

        if (request.getPassword() != null)
            user.setPassword(encoder.encode(request.getPassword()));

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        if (request.getRoles() != null){
            user.setRoles(Converter.stringsToRoles(roleRepository, request.getRoles()));
        }

        if(request.getFirstName() != null)
            user.getPersonalInformation().setFirstName(request.getFirstName());

        if(request.getLastName() != null)
            user.getPersonalInformation().setLastName(request.getLastName());

        if (request.getBirthdate() != null)
            user.getPersonalInformation().setBirthdate(request.getBirthdate());

        if (request.getCity() != null)
            user.getPersonalInformation().setCity(request.getCity());

        if (request.getCountry() != null)
            user.getPersonalInformation().setCountry(request.getCountry());

        if (request.getAddress() != null)
            user.getPersonalInformation().setAddress(request.getAddress());

        if (request.getPhone() != null)
            user.getPersonalInformation().setPhone(request.getPhone());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}
