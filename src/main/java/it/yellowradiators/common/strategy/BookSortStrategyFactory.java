package it.yellowradiators.common.strategy;

public class BookSortStrategyFactory {
    public static BookSortStrategy getStrategy(BookOrderType orderType) {
        return switch (orderType) {
            case AUTHOR      -> new AuthorSortStrategy();
            case TITLE_A_Z   -> new TitleSortStrategy();
            case MOST_RECENT -> new MostRecentSortStrategy();
        };
    }
}
