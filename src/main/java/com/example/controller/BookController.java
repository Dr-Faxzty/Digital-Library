package com.example.controller;

import com.example.common.interfaces.IBook;
import com.example.manager.BookManager;
import com.example.model.Book;
import com.example.persistence.JsonBookManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BookController {
    private static BookController bookInstance;
    private final BookManager bookManager;
    private final JsonBookManager jsonBookManager;

    private BookController() {
        this.bookManager = BookManager.getInstance();
        this.jsonBookManager = JsonBookManager.getInstance();

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
        List<Book> concreteBooks = jsonBookManager.load();
        return new ArrayList<>(concreteBooks);
    }

    private void saveBooks() {
        List<Book> books = bookManager.getAll().stream()
                .map(book -> (Book) book)
                .toList();
        jsonBookManager.save(books);
    }

    public void loadBooksAsync(java.util.function.Consumer<List<IBook>> onSuccess, Runnable onError) {
        jsonBookManager.loadAsync(books -> {
            List<IBook> ibooks = books.stream()
                    .map(book -> (IBook) book)
                    .toList();
            bookManager.setInitialBooks(ibooks);
            onSuccess.accept(ibooks);
        }, onError);
    }

}
