package com.example.utils;

import com.example.common.enums.BookCategoryType;
import com.example.common.interfaces.IBook;
import com.example.common.strategy.BookOrderType;
import com.example.common.strategy.BookSortStrategy;
import com.example.common.strategy.BookSortStrategyFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BookQueryUtils {

    public static List<IBook> getFilteredBooks(List<IBook> books, BookCategoryType category, BookOrderType orderType) {
        BookSortStrategy strategy = BookSortStrategyFactory.getStrategy(orderType);

        return books.stream()
                .filter(book ->
                        category == BookCategoryType.ALL ||
                                book.getType().equalsIgnoreCase(category.getLabel())
                )
                .sorted(strategy.getComparator())
                .collect(Collectors.toList());
    }
}
