package common.strategy;

import common.interfaces.IBook;

import java.util.Comparator;

public class TitleSortStrategy implements BookSortStrategy {
    public Comparator<IBook> getComparator() {
        return Comparator.comparing(IBook::getTitle);
    }
}
