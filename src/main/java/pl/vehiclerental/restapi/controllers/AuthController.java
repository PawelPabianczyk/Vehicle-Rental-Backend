package pl.vehiclerental.restapi.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.models.Customer;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.request.LoginRequest;
import pl.vehiclerental.restapi.payload.request.SignupRequest;
import pl.vehiclerental.restapi.payload.response.JwtResponse;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.CustomerRepository;
import pl.vehiclerental.restapi.repository.PersonalInformationRepository;
import pl.vehiclerental.restapi.repository.RoleRepository;
import pl.vehiclerental.restapi.repository.UserRepository;
import pl.vehiclerental.restapi.security.jwt.JwtUtils;
import pl.vehiclerental.restapi.security.services.UserDetailsImpl;
import pl.vehiclerental.restapi.utilities.Converter;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PersonalInformationRepository personalInformationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse response = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPersonalInformation(),
                roles);

        User user = userRepository.findByUsername(loginRequest.getUsername()).get();

        if(user.getEmployee() != null)
            response.setIdEmployee(user.getEmployee().getId());

        if(user.getCustomer() != null)
            response.setIdCustomer(user.getCustomer().getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
            throws JSONException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (personalInformationRepository.existsByPhone(signUpRequest.getPhone())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phone number is already in use!"));
        }

        // Create new user's account
        PersonalInformation pi = new PersonalInformation(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getBirthdate(),
                signUpRequest.getAddress(),
                signUpRequest.getCity(),
                signUpRequest.getCountry(),
                signUpRequest.getPhone()
        );
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                pi);
        pi.setUser(user);

        Set<String> strRoles = signUpRequest.getRole();
        user.setRoles(Converter.stringsToRoles(roleRepository, strRoles));
        userRepository.save(user);

        if(strRoles == null){
            Customer customer = new Customer();
            customer.setDrivingLicenseNumber(null);
            customer.setVerified(false);
            customer.setUser(user);
            customer.setActive(true);

            customerRepository.save(customer);
        }

        JSONObject response = new JSONObject();
        response.put("message", "User registered successfully!");
        response.put("id", user.getId());

        return ResponseEntity.ok(response.toString());
    }
}