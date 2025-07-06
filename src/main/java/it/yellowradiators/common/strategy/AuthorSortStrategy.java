package it.yellowradiators.common.strategy;

import it.yellowradiators.common.interfaces.IBook;

import java.util.Comparator;

public class AuthorSortStrategy implements BookSortStrategy {
    public Comparator<IBook> getComparator() {
        return Comparator.comparing(IBook::getAuthor);
    }
}
