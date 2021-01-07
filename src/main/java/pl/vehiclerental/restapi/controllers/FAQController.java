package pl.vehiclerental.restapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vehiclerental.restapi.dtos.EmployeeDto;
import pl.vehiclerental.restapi.dtos.FAQDto;
import pl.vehiclerental.restapi.models.Employee;
import pl.vehiclerental.restapi.models.FAQ;
import pl.vehiclerental.restapi.repository.FAQRepository;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faq")
public class FAQController {

    @Autowired
    FAQRepository faqRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('REGULAR')")
    public List<FAQDto> getAllFAQ() {
        List<FAQ> employees = faqRepository.findAll();
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FAQDto convertToDto(FAQ faq){
        FAQDto faqDto = new ModelMapper().map(faq, FAQDto.class);
        //TODO usunac potem
        if(faq.getEmployee() == null){
            faqDto.setEmployeeId(null);
        }
        else{
            faqDto.setEmployeeId(faq.getEmployee().getId());
        }
        return faqDto;
    }
}
