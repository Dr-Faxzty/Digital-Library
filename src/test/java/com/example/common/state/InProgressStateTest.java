package com.example.common.state;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InProgressStateTest {

    @Test
    void testIsReturned() {
        LoanState state = new InProgressState();
        assertFalse(state.isReturned(), "InProgressState should not be returned");
    }

    @Test
    void testIsExpired() {
        LoanState state = new InProgressState();
        LocalDate anyDate = LocalDate.now().plusDays(10);
        assertFalse(state.isExpired(anyDate), "InProgressState should never be expired");
    }

    @Test
    void testIsInProgress() {
        LoanState state = new InProgressState();
        assertTrue(state.isInProgress(), "InProgressState should be in progress");
    }

    @Test
    void testGetName() {
        LoanState state = new InProgressState();
        assertEquals("IN_PROGRESS", state.getName(), "Name should be 'IN_PROGRESS'");
    }
}
