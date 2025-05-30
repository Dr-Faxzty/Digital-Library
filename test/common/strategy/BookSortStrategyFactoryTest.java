package common.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookSortStrategyFactoryTest {

    @Test
    void testGetAuthorStrategy() {
        BookSortStrategy strategy = BookSortStrategyFactory.getStrategy(BookOrderType.AUTHOR);
        assertInstanceOf(AuthorSortStrategy.class, strategy);
    }

    @Test
    void testGetTitleStrategy() {
        BookSortStrategy strategy = BookSortStrategyFactory.getStrategy(BookOrderType.TITLE_A_Z);
        assertInstanceOf(TitleSortStrategy.class, strategy);
    }

    @Test
    void testGetMostRecentStrategy() {
        BookSortStrategy strategy = BookSortStrategyFactory.getStrategy(BookOrderType.MOST_RECENT);
        assertInstanceOf(MostRecentSortStrategy.class, strategy);
    }
}
