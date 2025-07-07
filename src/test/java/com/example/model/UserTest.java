package com.example.model;

import com.example.common.enums.Role;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "DOEJHN01A01H501T", "john@gmail.com", "ciao123", Role.USER);
    }

    @Test
    void testGettersNameReturnDefault() {
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("DOEJHN01A01H501T", user.getTaxIdCode());
        assertEquals("john@gmail.com", user.getEmail());
        assertEquals("ciao123", user.getPassword());
        assertEquals(Role.USER, user.getRole());

    }

    @Test
    void testSettersChangeValues() {
        user.setName("Jane");
        user.setSurname("Smith");
        user.setTaxIdCode("SMTJNE02A01H501T");
        user.setEmail("jane@gmail.com");
        user.setPassword("hello456");
        user.setRole(Role.USER);
    }

    @Test
    void testToString(){
        String expected = "User {" +
                "name = 'John'" +
                ", surname = 'Doe'" +
                ", taxIdCode = 'DOEJHN01A01H501T'" +
                ", email = 'john@gmail.com'" +
                ", password = 'ciao123'" +
                ", role = 'USER'" +
                '}';
        assertEquals(expected, user.toString());
    }
}
