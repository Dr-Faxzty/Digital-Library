package common.strategy;

import model.Book;

import java.util.Comparator;

public interface BookSortStrategy {
    Comparator<Book> getComparator();
}
