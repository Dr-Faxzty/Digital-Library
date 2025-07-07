package com.example.common.interfaces;

import com.example.common.state.LoanState;

import java.time.LocalDate;

public interface ILoan{
    int getId();
    IBook getBook();
    IUser getUser();
    LocalDate getLoanDate();
    LocalDate getExpirationDate();
    LocalDate getReturnDate();

    void setBook(IBook book);
    void setUser(IUser user);
    void setLoanDate(LocalDate loanDate);
    void setExpirationDate(LocalDate expirationDate);
    void setReturnDate(LocalDate returnDate);

    LoanState getState();

    boolean isReturned();
    boolean isExpired();
    boolean isInProgress();

    boolean isNull();
}
