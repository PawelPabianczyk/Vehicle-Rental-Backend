package pl.vehiclerental.restapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vehiclerental.restapi.dtos.EmployeeDto;
import pl.vehiclerental.restapi.dtos.PersonalInformationDto;
import pl.vehiclerental.restapi.dtos.UserDto;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.models.Role;
import pl.vehiclerental.restapi.models.User;
import pl.vehiclerental.restapi.payload.response.AllUsersData;
import pl.vehiclerental.restapi.repository.PersonalInformationRepository;
import pl.vehiclerental.restapi.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalInformationRepository personalInformationRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public AllUsersData getAllUsers() {
        List<User> users = userRepository.findAll();
        List<PersonalInformation> personalInformationData = personalInformationRepository.findAll();
        return new AllUsersData(
                users.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()),
                personalInformationData.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList()));

    }

    private PersonalInformationDto convertToDto(PersonalInformation personalInformation) {
        PersonalInformationDto personalInformationDto = new ModelMapper().map(personalInformation, PersonalInformationDto.class);
        personalInformationDto.setUserId(personalInformation.getUser().getId());
        return personalInformationDto;
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        PersonalInformation pi = personalInformationRepository.findByUser(user).get();
        userDto.setPersonalInformationId(pi.getId());

        Set<String> userDtoRoles = new HashSet<>();

        Set<Role> userRoles = user.getRoles();

        for (Role r :
                userRoles) {
            userDtoRoles.add(r.getName().name());
        }

        userDto.setRoles(userDtoRoles);

        if (user.getCustomer() != null) {
            userDto.setCustomerId(user.getCustomer().getId());
        } else {
            userDto.setCustomerId(null);
        }

        if (user.getEmployee() != null) {
            userDto.setEmployeeId(user.getEmployee().getId());
        } else {
            userDto.setEmployeeId(null);
        }

        return userDto;
    }
}
