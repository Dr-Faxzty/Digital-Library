package it.yellowradiators.common.strategy;

import it.yellowradiators.common.interfaces.IBook;

import java.util.Comparator;

public interface BookSortStrategy {
    Comparator<IBook> getComparator();
}
