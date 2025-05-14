package controller;

import manager.BookManager;
import model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookFilterController {
    private final BookManager bookManager = BookManager.getInstance();

    public List<Book> getFilteredBooks(String category, String orderBy) {
        List<Book> books = bookManager.getAll();

        // Filtra per categoria
        if (!category.equalsIgnoreCase("All")) {
            books = books.stream()
                    .filter(book -> book.getType().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        // Ordina
        switch (orderBy) {
            case "Author" -> books.sort(Comparator.comparing(Book::getAuthor));
            case "Titles A-Z" -> books.sort(Comparator.comparing(Book::getTitle));
            case "Most recent" -> books.sort(Comparator.comparing(Book::getDate).reversed());
        }

        return books;
    }
}
