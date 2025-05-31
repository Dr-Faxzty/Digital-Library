package persistence;

import model.Book;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTemplateManagerTest {

    private File tempFile;
    private JsonTemplateManager<Book> manager;

    @BeforeEach
    void setUp() throws Exception {
        tempFile = File.createTempFile("book_test", ".json");
        tempFile.delete();

        manager = new JsonTemplateManager<>(tempFile.getAbsolutePath(), Book[].class) {};
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) tempFile.delete();
    }

    @Test
    void testSaveAndLoad() {
        Book book1 = new Book("111", "Clean Code", "Robert C. Martin", LocalDate.of(2008, 8, 1), "Science", true, "img1");
        Book book2 = new Book("222", "Effective Java", "Joshua Bloch", LocalDate.of(2018, 1, 6), "Science", true, "img2");

        boolean saved = manager.save(List.of(book1, book2));
        assertTrue(saved);

        List<Book> loaded = manager.load();
        assertEquals(2, loaded.size());
        assertEquals("Clean Code", loaded.get(0).getTitle());
        assertEquals("Effective Java", loaded.get(1).getTitle());
    }

    @Test
    void testLoadEmptyFileReturnsEmptyList() {
        List<Book> books = manager.load();
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void testSaveFailureReturnsFalse() {
        JsonTemplateManager<Book> invalidManager = new JsonTemplateManager<>("/path/invalid/file.json", Book[].class) {};
        boolean saved = invalidManager.save(List.of(new Book()));
        assertFalse(saved);
    }
}
