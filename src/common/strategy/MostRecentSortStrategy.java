package common.strategy;

import model.Book;

import java.util.Comparator;

public class MostRecentSortStrategy implements BookSortStrategy {
    @Override
    public Comparator<Book> getComparator() {
        return Comparator.comparing(Book::getDate).reversed();
    }
}
