package com.example.common.strategy;

import com.example.common.interfaces.IBook;

import java.util.Comparator;

public interface BookSortStrategy {
    Comparator<IBook> getComparator();
}
