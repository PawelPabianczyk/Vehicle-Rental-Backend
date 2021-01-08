package pl.vehiclerental.restapi.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.models.Role;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.PersonalInformationRepository;
import pl.vehiclerental.restapi.repository.UserRepository;

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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() throws JSONException {
        List<User> users = userRepository.findAll();

        JSONArray jUsers = new JSONArray();
        JSONObject jUser;
        for (User u :
                users) {
            jUser = new JSONObject();
            jUser.put("username", u.getUsername());
            jUser.put("email", u.getEmail());
            jUser.put("isActive", u.getActive());
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
    public ResponseEntity<?> deactivateUser(@RequestBody Long id){
        User user = userRepository.findById(id).get();
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
    public ResponseEntity<?> activateUser(@RequestBody Long id){
        User user = userRepository.findById(id).get();
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
}
