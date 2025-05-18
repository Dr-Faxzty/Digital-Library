package common.strategy;

import model.Book;

import java.util.Comparator;

public class TitleSortStrategy implements BookSortStrategy {
    public Comparator<Book> getComparator() {
        return Comparator.comparing(Book::getTitle);
    }
}
