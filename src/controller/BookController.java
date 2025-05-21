package controller;

import manager.BookManager;
import model.Book;
import persistence.JsonBookManager;

import java.util.List;
import java.util.function.Predicate;

public class BookController {
    private final BookManager bookManager;
    private final JsonBookManager jsonBookManager;

    public BookController() {
        this.bookManager = BookManager.getInstance();
        this.jsonBookManager = new JsonBookManager();

        List<Book> initialBooks = loadBooks();
        bookManager.setInitialBooks(initialBooks);
    }

    public boolean addBook(Book book) {
        boolean added = bookManager.addBook(book);
        if (added) saveBooks();
        return added;
    }

    public boolean removeBook(String isbn) {
        boolean removed = bookManager.removeBook(isbn);
        if (removed) saveBooks();
        return removed;
    }

    public boolean updateBook(String isbn, Book updatedBook) {
        boolean updated = bookManager.updateBook(isbn, updatedBook);
        if (updated) saveBooks();
        return updated;
    }

    public List<Book> getAllBooks() {
        return bookManager.getAll();
    }

    public List<Book> searchBooks(Predicate<Book> filter) {
        return bookManager.search(filter);
    }

    private List<Book> loadBooks() {
        return jsonBookManager.load();
    }

    private void saveBooks() {
        jsonBookManager.save(bookManager.getAll());
    }

    public void loadBooksAsync(java.util.function.Consumer<List<Book>> onSuccess, Runnable onError) {
        jsonBookManager.loadAsync(books -> {
            bookManager.setInitialBooks(books);
            onSuccess.accept(books);
        }, onError);
    }

}
