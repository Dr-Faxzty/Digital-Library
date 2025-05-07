package model;

import java.time.LocalDate;

public class NullBook extends Book {
    public NullBook() { super("", "", "", LocalDate.MIN, "", false, ""); }

    @Override
    public String toString() { return ""; }
}