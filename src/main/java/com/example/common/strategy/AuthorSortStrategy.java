package com.example.common.strategy;

import com.example.common.interfaces.IBook;

import java.util.Comparator;

public class AuthorSortStrategy implements BookSortStrategy {
    public Comparator<IBook> getComparator() {
        return Comparator.comparing(IBook::getAuthor);
    }
}
