package com.example.common.strategy;

import com.example.common.interfaces.IBook;
import com.example.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MostRecentSortStrategyTest {

    @Test
    void testComparatorSortsByDateDescending() {
        IBook book1 = createBook("Book A", LocalDate.of(2020, 1, 1));
        IBook book2 = createBook("Book B", LocalDate.of(2023, 1, 1));
        IBook book3 = createBook("Book C", LocalDate.of(2021, 1, 1));

        List<IBook> books = Arrays.asList(book1, book2, book3);
        books.sort(new MostRecentSortStrategy().getComparator());

        assertEquals("Book B", books.get(0).getTitle());
        assertEquals("Book C", books.get(1).getTitle());
        assertEquals("Book A", books.get(2).getTitle());
    }

    private IBook createBook(String title, LocalDate date) {
        return new Book() {
            @Override public String getTitle() { return title; }
            @Override public LocalDate getDate() { return date; }
        };
    }
}
