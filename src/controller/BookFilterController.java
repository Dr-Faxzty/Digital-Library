package controller;

import manager.BookManager;

import java.io.IOException;
import manager.BookManager;
import model.Book;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;


public class BookFilterController {
    private final BookManager bookManager = BookManager.getInstance();

    public List<Book> getFilteredBooks(String category, String orderBy) {
        return bookManager.search(book ->
                        category.equalsIgnoreCase("All") || book.getType().equalsIgnoreCase(category)
                ).stream()
                .sorted(getComparator(orderBy))
                .collect(Collectors.toList());
    }

    private Comparator<Book> getComparator(String orderBy) {
        return switch (orderBy) {
            case "Author" -> Comparator.comparing(Book::getAuthor);
            case "Titles A-Z" -> Comparator.comparing(Book::getTitle);
            case "Most recent" -> Comparator.comparing(Book::getDate).reversed();
            default -> Comparator.comparing(Book::getTitle);
        };
    }
}
