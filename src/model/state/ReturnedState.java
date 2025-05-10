package model.state;

import common.interfaces.LoanState;

import java.time.LocalDate;

public class ReturnedState implements LoanState {
    public boolean isReturned() { return true; }
    public boolean isExpired(LocalDate expirationDate) { return false; }
    public boolean isInProgress() { return false; }
    public String getName() { return "RETURNED"; }
}