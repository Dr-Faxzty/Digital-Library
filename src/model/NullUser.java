package model;

public class NullUser extends User {
    public NullUser() { super("", "", "", "", "", Role.USER); }

    @Override
    public String toString() { return ""; }
}