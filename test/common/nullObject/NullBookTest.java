package common.nullObject;

import common.interfaces.IBook;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class NullBookTest {

    private final IBook nullBook = new NullBook();

    @Test
    void testIsNullReturnsTrue() {
        assertTrue(nullBook.isNull());
    }

    @Test
    void testGettersReturnDefaults() {
        assertEquals(" ", nullBook.getIsbn());
        assertEquals(" ", nullBook.getTitle());
        assertEquals(" ", nullBook.getAuthor());
        assertEquals(" ", nullBook.getType());
        assertEquals(" ", nullBook.getUrlImage());
        assertFalse(nullBook.available());
        assertNull(nullBook.getDate());
    }

    @Test
    void testToStringIsEmpty() {
        assertEquals("", nullBook.toString());
    }

    @Test
    void testSettersDoNotThrow() {
        assertDoesNotThrow(() -> {
            nullBook.setTitle("X");
            nullBook.setIsbn("X");
            nullBook.setAuthor("X");
            nullBook.setDate(LocalDate.now());
            nullBook.setType("X");
            nullBook.setAvailable(true);
            nullBook.setUrlImage("X");
        });
    }
}
