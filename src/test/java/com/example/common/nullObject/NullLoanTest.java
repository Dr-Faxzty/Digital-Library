package com.example.common.nullObject;

import com.example.common.interfaces.ILoan;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class NullLoanTest {

    private final ILoan nullLoan = new NullLoan();

    @Test
    void testIsNullReturnsTrue() {
        assertTrue(nullLoan.isNull());
    }

    @Test
    void testGettersReturnDefaults() {
        assertEquals(0, nullLoan.getId());
        assertNull(nullLoan.getBook());
        assertNull(nullLoan.getUser());
        assertNull(nullLoan.getLoanDate());
        assertNull(nullLoan.getExpirationDate());
        assertNull(nullLoan.getReturnDate());
        assertNull(nullLoan.getState());
    }

    @Test
    void testStateBooleansAreFalse() {
        assertFalse(nullLoan.isReturned());
        assertFalse(nullLoan.isExpired());
        assertFalse(nullLoan.isInProgress());
    }

    @Test
    void testToStringIsEmpty() {
        assertEquals("", nullLoan.toString());
    }

    @Test
    void testSettersDoNotThrow() {
        assertDoesNotThrow(() -> {
            nullLoan.setBook(null);
            nullLoan.setUser(null);
            nullLoan.setLoanDate(LocalDate.now());
            nullLoan.setExpirationDate(LocalDate.now());
            nullLoan.setReturnDate(LocalDate.now());
        });
    }
}
