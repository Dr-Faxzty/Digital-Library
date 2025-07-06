package it.yellowradiators.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("1234567890", "Title", "Author", LocalDate.of(2022, 5, 10), "fiction", true, "http://example.com/image.jpg");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals(LocalDate.of(2022, 5, 10), book.getDate());
        assertEquals("fiction", book.getType());
        assertTrue(book.available());
        assertEquals("http://example.com/image.jpg", book.getUrlImage());
    }

    @Test
    void testSetters() {
        book.setIsbn("0987654321");
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setDate(LocalDate.of(2023, 1, 1));
        book.setType("non-fiction");
        book.setAvailable(false);
        book.setUrlImage("http://new.com/img.jpg");

        assertEquals("0987654321", book.getIsbn());
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals(LocalDate.of(2023, 1, 1), book.getDate());
        assertEquals("non-fiction", book.getType());
        assertFalse(book.available());
        assertEquals("http://new.com/img.jpg", book.getUrlImage());
    }

    @Test
    void testIsNullReturnsFalse() {
        assertFalse(book.isNull());
    }

    @Test
    void testToStringContainsTitleAndAuthor() {
        String str = book.toString();
        assertTrue(str.contains("Title"));
        assertTrue(str.contains("Author"));
    }
}
