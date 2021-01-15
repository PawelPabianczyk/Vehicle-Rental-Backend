package pl.vehiclerental.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.vehiclerental.restapi.dtos.FAQDto;
import pl.vehiclerental.restapi.models.*;
import pl.vehiclerental.restapi.payload.response.MessageResponse;
import pl.vehiclerental.restapi.repository.EmployeeRepository;
import pl.vehiclerental.restapi.repository.FAQRepository;
import pl.vehiclerental.restapi.utilities.Converter;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faq")
public class FAQController {

    @Autowired
    FAQRepository faqRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('REGULAR')")
    public List<FAQDto> getAllFAQ() {
        List<FAQ> employees = faqRepository.findAll();
        return employees.stream()
                .map(Converter::convertFAQToFAQDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<FAQDto> getAllActiveFAQ() {
        List<FAQ> employees = faqRepository.findAllByIsActive(true);
        return employees.stream()
                .map(Converter::convertFAQToFAQDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> deactivateFAQ(@RequestBody FAQDto faqDto){
        FAQ faq = faqRepository.findById(faqDto.getId()).get();
        faq.setActive(false);
        faqRepository.save(faq);
        return ResponseEntity.ok(new MessageResponse("FAQ deactivated successfully!"));
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> activateFAQ(@RequestBody FAQDto faqDto){
        FAQ faq = faqRepository.findById(faqDto.getId()).get();
        faq.setActive(true);
        faqRepository.save(faq);
        return ResponseEntity.ok(new MessageResponse("FAQ activated successfully!"));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> addFAQ(@RequestBody FAQDto faqDto){
        Employee employee = employeeRepository.findById(faqDto.getEmployeeId()).get();
        FAQ faq = new FAQ();
        faq.setActive(true);
        faq.setEmployee(employee);
        faq.setAnswer(faqDto.getAnswer());
        faq.setQuestion(faqDto.getQuestion());
        faqRepository.save(faq);
        return ResponseEntity.ok(new MessageResponse("FAQ added successfully!"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<?> updateFAQ(@RequestBody FAQDto faqDto){
        FAQ faq = faqRepository.findById(faqDto.getId()).get();
        faq.setActive(true);
        faq.setAnswer(faqDto.getAnswer());
        faq.setQuestion(faqDto.getQuestion());
        faqRepository.save(faq);
        return ResponseEntity.ok(new MessageResponse("FAQ updated successfully!"));
    }
}
