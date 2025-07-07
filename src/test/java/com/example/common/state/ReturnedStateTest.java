package com.example.common.state;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReturnedStateTest {

    @Test
    void testIsReturned() {
        LoanState state = new ReturnedState();
        assertTrue(state.isReturned(), "ReturnedState should be returned");
    }

    @Test
    void testIsExpired() {
        LoanState state = new ReturnedState();
        LocalDate anyDate = LocalDate.now().minusDays(100);
        assertFalse(state.isExpired(anyDate), "ReturnedState should never be expired");
    }

    @Test
    void testIsInProgress() {
        LoanState state = new ReturnedState();
        assertFalse(state.isInProgress(), "ReturnedState should not be in progress");
    }

    @Test
    void testGetName() {
        LoanState state = new ReturnedState();
        assertEquals("RETURNED", state.getName(), "Name should be 'RETURNED'");
    }
}
