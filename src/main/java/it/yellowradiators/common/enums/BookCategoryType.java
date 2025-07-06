package it.yellowradiators.common.enums;

public enum BookCategoryType {
    ALL("All"),
    NARRATIVE("Narrative"),
    NON_FICTION("Non-fiction"),
    SCIENCE("Science"),
    HISTORY("History"),
    BIOGRAPHY("Biography");

    private final String label;

    BookCategoryType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static BookCategoryType fromLabel(String label) {
        for (BookCategoryType type : values()) {
            if (type.getLabel().equalsIgnoreCase(label)) {
                return type;
            }
        }
        return ALL;
    }
}
