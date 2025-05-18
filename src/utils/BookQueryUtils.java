package utils;

import common.enums.BookCategoryType;
import model.Book;
import common.strategy.BookSortStrategy;
import common.strategy.BookSortStrategyFactory;
import common.strategy.BookOrderType;

import java.util.List;
import java.util.stream.Collectors;

public class BookQueryUtils {

    public static List<Book> getFilteredBooks(List<Book> books, BookCategoryType category, BookOrderType orderType) {
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
