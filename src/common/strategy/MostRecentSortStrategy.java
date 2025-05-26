package common.strategy;

import common.interfaces.IBook;

import java.util.Comparator;

public class MostRecentSortStrategy implements BookSortStrategy {
    @Override
    public Comparator<IBook> getComparator() {
        return Comparator.comparing(IBook::getDate).reversed();
    }
}
