package com.example.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void testIsEmailValid_Valid() {
        assertTrue(InputValidator.isEmailValid("test@example.com"));
    }

    @Test
    void testIsEmailValid_Invalid() {
        assertFalse(InputValidator.isEmailValid("missing@domain"));
        assertFalse(InputValidator.isEmailValid(null));
    }

    @Test
    void testIsTaxCodeValid_Valid() {
        assertTrue(InputValidator.isTaxCodeValid("RSSMRA85M01H501Z"));
    }

    @Test
    void testIsTaxCodeValid_Invalid() {
        assertFalse(InputValidator.isTaxCodeValid("INVALID123"));
        assertFalse(InputValidator.isTaxCodeValid("1234567890123456"));
        assertFalse(InputValidator.isTaxCodeValid(null));
    }

    @Test
    void testIsPasswordValid() {
        assertTrue(InputValidator.isPasswordValid("abcdef"));
        assertFalse(InputValidator.isPasswordValid("abc"));
        assertFalse(InputValidator.isPasswordValid(null));
    }

    @Test
    void testAreFieldsFilled_AllFilled() {
        assertTrue(InputValidator.areFieldsFilled("a", "b", "c"));
    }

    @Test
    void testAreFieldsFilled_OneEmpty() {
        assertFalse(InputValidator.areFieldsFilled("a", "", "c"));
    }

    @Test
    void testAreFieldsFilled_OneNull() {
        assertFalse(InputValidator.areFieldsFilled("a", null, "c"));
    }

    @Test
    void testAreFieldsFilled_AllEmptyOrNull() {
        assertFalse(InputValidator.areFieldsFilled("", " ", null));
    }
}
