package model;

import java.time.LocalDate;

public class NullLoan extends Loan {
    public NullLoan() { super(0, new NullBook(), new NullUser(), LocalDate.MIN, LocalDate.MIN); }

    @Override
    public String toString() { return ""; }
}