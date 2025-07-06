package it.yellowradiators.utils;

import it.yellowradiators.common.enums.BookCategoryType;
import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.strategy.BookOrderType;
import it.yellowradiators.common.strategy.BookSortStrategy;
import it.yellowradiators.common.strategy.BookSortStrategyFactory;

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
