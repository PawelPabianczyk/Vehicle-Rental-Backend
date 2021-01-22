package pl.vehiclerental.restapi.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import pl.vehiclerental.restapi.dtos.FAQDto;
import pl.vehiclerental.restapi.models.FAQ;
import pl.vehiclerental.restapi.repository.EmployeeRepository;
import pl.vehiclerental.restapi.repository.FAQRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FAQControllerTest {

    @Autowired
    FAQController faqController;

    @Autowired
    FAQRepository faqRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(faqController).isNotNull();
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void getAllFAQ() {
        List<FAQDto> faqsDto = faqController.getAllFAQ();
        List<FAQ> faqs = faqRepository.findAll();
        assertThat(faqs.size()).isEqualTo(faqsDto.size());
    }

    @Test
    void getAllActiveFAQ() {
        List<FAQDto> faqsDto = faqController.getAllActiveFAQ();
        List<FAQ> faqs = faqRepository.findAllByIsActive(true);
        assertThat(faqs.size()).isEqualTo(faqsDto.size());
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void deactivateFAQ() {
        FAQDto faqDto = new FAQDto();
        faqDto.setId(1L);
        faqController.deactivateFAQ(faqDto);
        FAQ faqDB = faqRepository.findById(faqDto.getId()).get();
        assertThat(faqDB.getActive()).isFalse();
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void activateFAQ() {
        FAQDto faqDto = new FAQDto();
        faqDto.setId(1L);
        faqController.activateFAQ(faqDto);
        FAQ faqDB = faqRepository.findById(faqDto.getId()).get();
        assertThat(faqDB.getActive()).isTrue();
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void addFAQ() {
        FAQDto faqDto = new FAQDto();
        faqDto.setEmployeeId(1L);
        faqDto.setQuestion("Test1");
        faqDto.setAnswer("Test1");
        faqController.addFAQ(faqDto);
        FAQ faqDB = faqRepository.findByQuestionAndAnswer("Test1","Test1").get();
        assertThat(faqDB).isNotNull();
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void updateFAQ() {
        FAQDto faqDto = new FAQDto();
        faqDto.setId(1L);
        faqDto.setQuestion("Test1");
        faqDto.setAnswer("Test1");
        faqController.updateFAQ(faqDto);
        FAQ faqDB = faqRepository.findByQuestionAndAnswer("Test1","Test1").get();
        assertThat(faqDB).isNotNull();
    }
}