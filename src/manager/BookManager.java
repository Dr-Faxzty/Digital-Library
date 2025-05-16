package manager;

import common.interfaces.Manager;
import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookManager implements Manager<Book> {
    private static BookManager instance;
    private final List<Book> books = new ArrayList<>();

    public BookManager() {}

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public void setInitialBooks(List<Book> initialBooks) {
        books.clear();
        books.addAll(initialBooks);
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

    @Override
    public List<Book> search(Predicate<Book> filter) {
        return books.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(books);
    }
}