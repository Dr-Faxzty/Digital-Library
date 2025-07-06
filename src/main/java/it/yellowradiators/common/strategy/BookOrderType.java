package it.yellowradiators.common.strategy;

public enum BookOrderType {
    MOST_RECENT("Most recent"),
    AUTHOR("Author"),
    TITLE_A_Z("Titles A-Z");

    private final String label;

    BookOrderType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static BookOrderType fromLabel(String label) {
        for (BookOrderType type : values()) {
            if (type.getLabel().equalsIgnoreCase(label)) {
                return type;
            }
        }
        return TITLE_A_Z;
    }
}
