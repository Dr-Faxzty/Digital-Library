package it.yellowradiators.common.enums;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Role fromLabel(String label) {
        for (Role role : values()) {
            if (role.getLabel().equalsIgnoreCase(label)) {
                return role;
            }
        }
        return USER;
    }
}
