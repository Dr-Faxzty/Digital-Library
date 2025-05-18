package common.strategy;

import model.Book;

import java.util.Comparator;

public class AuthorSortStrategy implements BookSortStrategy {
    public Comparator<Book> getComparator() {
        return Comparator.comparing(Book::getAuthor);
    }
}
