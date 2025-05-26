package common.strategy;

import common.interfaces.IBook;

import java.util.Comparator;

public interface BookSortStrategy {
    Comparator<IBook> getComparator();
}
