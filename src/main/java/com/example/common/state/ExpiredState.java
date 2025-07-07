package com.example.common.state;

import java.time.LocalDate;

public class ExpiredState implements LoanState {
    public boolean isReturned() { return false; }
    public boolean isExpired(LocalDate expirationDate) { return LocalDate.now().isAfter(expirationDate); }
    public boolean isInProgress() { return false; }
    public String getName() { return "EXPIRED"; }
}