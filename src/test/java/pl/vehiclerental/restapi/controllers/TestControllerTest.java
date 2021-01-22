package pl.vehiclerental.restapi.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @Autowired
    TestController testController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(testController).isNotNull();
    }

    @Test
    public void allAccess() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/test/all",
                String.class)).contains("Public Content.");
    }

    @Test
    @WithMockUser(username="user",roles = {"USER"})
    void userAccess() {
        String response = testController.userAccess();
        Assert.assertEquals("User Content.", response);
    }

    @Test
    @WithMockUser(username="regular",roles = {"REGULAR"})
    void employeeAccess() {
        String response = testController.employeeAccess();
        Assert.assertEquals("Employee Board.", response);
    }

    @Test
    @WithMockUser(username="manager",roles = {"MANAGER"})
    void managerAccess() {
        String response = testController.managerAccess();
        Assert.assertEquals("Manager Board.", response);
    }

    @Test
    @WithMockUser(username="admin",roles = {"ADMIN"})
    void adminAccess() {
        String response = testController.adminAccess();
        Assert.assertEquals("Admin Board.", response);
    }
}