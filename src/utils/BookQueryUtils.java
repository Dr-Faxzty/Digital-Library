package utils;

import common.enums.BookCategoryType;
import common.interfaces.IBook;
import common.strategy.BookSortStrategy;
import common.strategy.BookSortStrategyFactory;
import common.strategy.BookOrderType;

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
