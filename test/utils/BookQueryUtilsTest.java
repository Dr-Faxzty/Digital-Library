package utils;

import common.enums.BookCategoryType;
import common.interfaces.IBook;
import common.strategy.BookOrderType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookQueryUtilsTest {

    private final IBook book1 = new DummyBook("Alpha", "Zorro", "Narrative", LocalDate.of(2020, 1, 1));
    private final IBook book2 = new DummyBook("Beta", "Alice", "Non-fiction", LocalDate.of(2022, 1, 1));
    private final IBook book3 = new DummyBook("Gamma", "Bob", "Narrative", LocalDate.of(2021, 1, 1));
    private final List<IBook> books = List.of(book1, book2, book3);

    @Test
    void testFilterAllCategories() {
        List<IBook> result = BookQueryUtils.getFilteredBooks(books, BookCategoryType.ALL, BookOrderType.TITLE_A_Z);
        assertEquals(3, result.size());
    }

    @Test
    void testFilterNarrativeOnly() {
        List<IBook> result = BookQueryUtils.getFilteredBooks(books, BookCategoryType.NARRATIVE, BookOrderType.TITLE_A_Z);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(b -> b.getType().equalsIgnoreCase("Narrative")));
    }


    @Test
    void testSortByAuthor() {
        List<IBook> result = BookQueryUtils.getFilteredBooks(books, BookCategoryType.ALL, BookOrderType.AUTHOR);
        assertEquals("Alice", result.get(0).getAuthor());
        assertEquals("Bob", result.get(1).getAuthor());
        assertEquals("Zorro", result.get(2).getAuthor());
    }

    @Test
    void testSortByMostRecent() {
        List<IBook> result = BookQueryUtils.getFilteredBooks(books, BookCategoryType.ALL, BookOrderType.MOST_RECENT);
        assertEquals(book2, result.get(0));
        assertEquals(book3, result.get(1));
        assertEquals(book1, result.get(2));
    }

    static class DummyBook implements IBook {
        private final String title;
        private final String author;
        private final String type;
        private final LocalDate date;

        DummyBook(String title, String author, String type, LocalDate date) {
            this.title = title;
            this.author = author;
            this.type = type;
            this.date = date;
        }

        public String getIsbn() { return ""; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public LocalDate getDate() { return date; }
        public String getType() { return type; }
        public boolean available() { return true; }
        public String getUrlImage() { return ""; }

        public void setIsbn(String isbn) {}
        public void setTitle(String title) {}
        public void setAuthor(String author) {}
        public void setDate(LocalDate date) {}
        public void setType(String type) {}
        public void setAvailable(boolean available) {}
        public void setUrlImage(String urlImage) {}

        public boolean isNull() { return false; }
    }
}
