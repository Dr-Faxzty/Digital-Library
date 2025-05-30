package common.state;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ExpiredStateTest {

    private final LoanState state = new ExpiredState();

    @Test
    void testIsExpired() {
        assertTrue(state.isExpired(LocalDate.now().minusDays(1)));
    }

    @Test
    void testIsReturnedIsFalse() {
        assertFalse(state.isReturned());
    }

    @Test
    void testIsInProgressIsFalse() {
        assertFalse(state.isInProgress());
    }

    @Test
    void testGetName() {
        assertEquals("EXPIRED", state.getName());
    }
}
