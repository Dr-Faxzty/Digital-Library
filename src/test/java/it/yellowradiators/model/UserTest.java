package test.java.it.yellowradiators.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private it.yellowradiators.model.User user;

    @BeforeEach
    void setUp() {
        user = new it.yellowradiators.model.User("John", "Doe", "DOEJHN01A01H501T", "john@gmail.com", "ciao123", it.yellowradiators.common.enums.Role.USER);
    }

    @Test
    void testGettersNameReturnDefault() {
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("DOEJHN01A01H501T", user.getTaxIdCode());
        assertEquals("jhon@gmail.com", user.getEmail());
        assertEquals("ciao123", user.getPassword());
        assertEquals(it.yellowradiators.common.enums.Role.USER, user.getRole());

    }

    @Test
    void testSettersChangeValues() {
        user.setName("Jane");
        user.setSurname("Smith");
        user.setTaxIdCode("SMTJNE02A01H501T");
        user.setEmail("jane@gmail.com");
        user.setPassword("hello456");
        user.setRole(it.yellowradiators.common.enums.Role.USER);
    }

    @Test
    void testToString(){
        String expected = "User {" +
                "name = 'John'" +
                ", surname = 'Doe'" +
                ", taxIdCode = 'DOEJHN01A01H501T'" +
                ", email = 'jhon@gmail.com'" +
                ", password = 'ciao123'" +
                ", role = 'USER'" +
                '}';
        assertEquals(expected, user.toString());
    }
}
