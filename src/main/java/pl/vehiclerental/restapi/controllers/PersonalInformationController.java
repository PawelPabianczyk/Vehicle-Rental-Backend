package pl.vehiclerental.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vehiclerental.restapi.dtos.PersonalInformationDto;
import pl.vehiclerental.restapi.models.PersonalInformation;
import pl.vehiclerental.restapi.repository.PersonalInformationRepository;
import pl.vehiclerental.restapi.utilities.Converter;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/personalinformation")
public class PersonalInformationController {

    @Autowired
    PersonalInformationRepository personalInformationRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PersonalInformationDto> getAllPersonalInformation() {
        List<PersonalInformation> personalInformationData = personalInformationRepository.findAll();
        return personalInformationData.stream()
                .map(Converter::convertPersonalInformationToPersonalInformationDto)
                .collect(Collectors.toList());
    }
}
