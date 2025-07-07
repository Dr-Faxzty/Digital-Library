package com.example.common.strategy;

import com.example.common.interfaces.IBook;
import com.example.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TitleSortStrategyTest {

    @Test
    void testComparatorSortsByTitleAscending() {
        IBook book1 = createBook("Zebra", LocalDate.now());
        IBook book2 = createBook("Apple", LocalDate.now());
        IBook book3 = createBook("Monkey", LocalDate.now());

        List<IBook> books = Arrays.asList(book1, book2, book3);
        books.sort(new TitleSortStrategy().getComparator());

        assertEquals("Apple", books.get(0).getTitle());
        assertEquals("Monkey", books.get(1).getTitle());
        assertEquals("Zebra", books.get(2).getTitle());
    }

    private IBook createBook(String title, LocalDate date) {
        return new Book() {
            @Override public String getTitle() { return title; }
            @Override public LocalDate getDate() { return date; }
        };
    }
}
