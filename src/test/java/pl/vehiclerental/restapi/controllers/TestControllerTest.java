package pl.vehiclerental.restapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerTest {

    private TestController testController;

    @BeforeEach
    void setUp() {
        testController = new TestController();
    }

    @Test
    void allAccess() {
        String response = testController.allAccess();
        assertEquals("Public Content.",response);
    }

    @Test
    void userAccess() {
        String response = testController.userAccess();
        assertEquals("User Content.",response);
    }

    @Test
    void employeeAccess() {
        String response = testController.employeeAccess();
        assertEquals("Employee Board.",response);
    }

    @Test
    void managerAccess() {
        String response = testController.managerAccess();
        assertEquals("Manager Board.",response);
    }

    @Test
    void adminAccess() {
        String response = testController.adminAccess();
        assertEquals("Admin Board.",response);
    }
}