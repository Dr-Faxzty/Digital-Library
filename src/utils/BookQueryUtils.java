package utils;

import model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookQueryUtils {
    public static List<Book> getFilteredBooks(List<Book> books, String category, String orderBy) {
        return books.stream()
                .filter(book -> category.equalsIgnoreCase("All") || book.getType().equalsIgnoreCase(category))
                .sorted(getComparator(orderBy))
                .collect(Collectors.toList());
    }

    private static Comparator<Book> getComparator(String orderBy) {
        return switch (orderBy) {
            case "Author"      -> Comparator.comparing(Book::getAuthor);
            case "Titles A-Z"  -> Comparator.comparing(Book::getTitle);
            case "Most recent" -> Comparator.comparing(Book::getDate).reversed();
            default            -> Comparator.comparing(Book::getTitle);
        };
    }
}
