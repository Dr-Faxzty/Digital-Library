package com.example.common.nullObject;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NullUserTest {
    private IUser nullUser;

    @BeforeEach
    void setUp() {
        nullUser = new NullUser();
    }

    @Test
    void testIsNullReturnsTrue() {
        assert nullUser.isNull();
    }

    @Test
    void testGettersReturnDefaults() {
        assert "".equals(nullUser.getName());
        assert "".equals(nullUser.getSurname());
        assert "".equals(nullUser.getTaxIdCode());
        assert "".equals(nullUser.getEmail());
        assert "".equals(nullUser.getPassword());
        assert Role.USER.equals(nullUser.getRole());
    }

    @Test
    void testToStringIsEmpty() {
        assert "".equals(nullUser.toString());
    }

    @Test
    void testSettersDoNotThrow() {
        nullUser.setName("X");
        nullUser.setSurname("X");
        nullUser.setTaxIdCode("X");
        nullUser.setEmail("X");
        nullUser.setPassword("X");
        nullUser.setRole(Role.ADMIN);
    }
}
