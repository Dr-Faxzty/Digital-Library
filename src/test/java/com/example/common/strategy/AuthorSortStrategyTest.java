package com.example.common.strategy;

import com.example.common.interfaces.IBook;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorSortStrategyTest {

    @Test
    void testAuthorSorting() {
        IBook book1 = new DummyBook("Zebra");
        IBook book2 = new DummyBook("Alice");
        IBook book3 = new DummyBook("Bob");

        List<IBook> books = new ArrayList<>(List.of(book1, book2, book3));
        books.sort(new AuthorSortStrategy().getComparator());

        assertEquals("Alice", books.get(0).getAuthor());
        assertEquals("Bob", books.get(1).getAuthor());
        assertEquals("Zebra", books.get(2).getAuthor());
    }

    static class DummyBook implements IBook {
        private final String author;

        DummyBook(String author) { this.author = author; }

        public String getAuthor() { return author; }

        public String getIsbn() { return ""; }
        public String getTitle() { return ""; }
        public java.time.LocalDate getDate() { return null; }
        public String getType() { return ""; }
        public boolean available() { return true; }
        public String getUrlImage() { return ""; }

        public void setIsbn(String isbn) {}
        public void setTitle(String title) {}
        public void setAuthor(String author) {}
        public void setDate(java.time.LocalDate date) {}
        public void setType(String type) {}
        public void setAvailable(boolean available) {}
        public void setUrlImage(String urlImage) {}

        public boolean isNull() { return false; }
    }
}
