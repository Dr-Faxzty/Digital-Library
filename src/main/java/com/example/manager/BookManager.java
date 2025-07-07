package com.example.manager;

import com.example.common.interfaces.IBook;
import com.example.common.interfaces.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookManager implements Manager<IBook> {
    private static BookManager instance;
    private final List<IBook> books = new ArrayList<>();

    public BookManager() {}

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public void setInitialBooks(List<IBook> initialBooks) {
        books.clear();
        books.addAll(initialBooks);
    }

    public boolean addBook(IBook book) {
        boolean exists = books.stream().anyMatch(b -> b.getIsbn().equals(book.getIsbn()));
        if (exists) return false;
        books.add(book);
        return true;
    }

    public boolean removeBook(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    public boolean updateBook(String isbn, IBook updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<IBook> search(Predicate<IBook> filter) {
        return books.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public List<IBook> getAll() {
        return new ArrayList<>(books);
    }
}