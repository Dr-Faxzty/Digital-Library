package manager;

import common.interfaces.IBook;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookManagerTest {

    private BookManager manager;
    private IBook book1, book2;

    @BeforeEach
    void setUp() {
        manager = new BookManager();
        book1 = new Book("123", "Title A", "Author A", LocalDate.of(2020, 1, 1), "Science", true, "img1");
        book2 = new Book("456", "Title B", "Author B", LocalDate.of(2021, 2, 2), "Fantasy", false, "img2");
    }

    @Test
    void testSetInitialBooks() {
        manager.setInitialBooks(List.of(book1, book2));
        List<IBook> all = manager.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void testAddBookSuccess() {
        boolean added = manager.addBook(book1);
        assertTrue(added);
        assertEquals(1, manager.getAll().size());
    }

    @Test
    void testAddBookDuplicateIsbn() {
        manager.addBook(book1);
        Book duplicate = new Book("123", "Other", "Other", LocalDate.now(), "Science", true, "img3");
        boolean added = manager.addBook(duplicate);
        assertFalse(added);
        assertEquals(1, manager.getAll().size());
    }

    @Test
    void testRemoveBook() {
        manager.setInitialBooks(List.of(book1, book2));
        boolean removed = manager.removeBook("456");
        assertTrue(removed);
        assertEquals(1, manager.getAll().size());
    }

    @Test
    void testUpdateBook() {
        manager.addBook(book1);
        Book updated = new Book("123", "Updated Title", "Author A", LocalDate.now(), "Science", true, "img1");
        boolean result = manager.updateBook("123", updated);
        assertTrue(result);
        assertEquals("Updated Title", manager.getAll().getFirst().getTitle());
    }

    @Test
    void testSearchByAuthor() {
        manager.setInitialBooks(List.of(book1, book2));
        List<IBook> results = manager.search(book -> book.getAuthor().equals("Author A"));
        assertEquals(1, results.size());
        assertEquals("Author A", results.getFirst().getAuthor());
    }
}
