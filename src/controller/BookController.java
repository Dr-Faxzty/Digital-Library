package controller;

import manager.BookManager;
import persistence.JsonBookManager;

import java.util.List;
import java.util.function.Predicate;
import common.interfaces.IBook;

public class BookController {
    private static BookController bookInstance;
    private final BookManager bookManager;
    private final JsonBookManager jsonBookManager;

    private BookController() {
        this.bookManager = BookManager.getInstance();
        this.jsonBookManager = new JsonBookManager();

        List<IBook> initialBooks = loadBooks();
        bookManager.setInitialBooks(initialBooks);
    }

    public static BookController getInstance() {
        if(bookInstance == null){
            bookInstance = new BookController();
        }
        return bookInstance;
    }

    public boolean addBook(IBook book) {
        boolean added = bookManager.addBook(book);
        if (added) saveBooks();
        return added;
    }

    public boolean removeBook(String isbn) {
        boolean removed = bookManager.removeBook(isbn);
        if (removed) saveBooks();
        return removed;
    }

    public boolean updateBook(String isbn, IBook updatedBook) {
        boolean updated = bookManager.updateBook(isbn, updatedBook);
        if (updated) saveBooks();
        return updated;
    }

    public List<IBook> getAllBooks() {
        return bookManager.getAll();
    }

    public List<IBook> searchBooks(Predicate<IBook> filter) {
        return bookManager.search(filter);
    }

    private List<IBook> loadBooks() {
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
