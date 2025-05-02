package manager;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookManager {
    private final List<Book> books = new ArrayList<>();

    public BookManager(List<Book> initialBooks) {
        if (initialBooks != null) {
            books.addAll(initialBooks);
        }
    }

    public boolean addBook(Book book) {
        boolean exists = books.stream().anyMatch(b -> b.getIsbn().equals(book.getIsbn()));
        if (exists) return false;
        books.add(book);
        return true;
    }

    public boolean removeBook(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    public boolean updateBook(String isbn, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    public List<Book> search(Predicate<Book> filter) {
        return books.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}
