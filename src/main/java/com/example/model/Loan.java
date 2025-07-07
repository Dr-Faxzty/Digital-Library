package com.example.model;

import com.example.common.interfaces.IBook;
import com.example.common.interfaces.ILoan;
import com.example.common.interfaces.IUser;
import com.example.common.state.ExpiredState;
import com.example.common.state.InProgressState;
import com.example.common.state.LoanState;
import com.example.common.state.ReturnedState;

import java.time.LocalDate;

public class Loan implements ILoan {
    private final int id;
    private IBook book;
    private IUser user;
    private LocalDate loanDate;
    private LocalDate expirationDate;
    private LocalDate returnDate;
    private transient LoanState state;

    public Loan() { this.id = 0; }

    public Loan(int id, IBook book, IUser user, LocalDate loanDate, LocalDate expirationDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.expirationDate = expirationDate;
        this.returnDate = null;
        updateState();
    }

    @Override public int getId() { return this.id; }
    @Override public IBook getBook() { return this.book; }
    @Override public IUser getUser() { return this.user; }
    @Override public LocalDate getLoanDate() { return this.loanDate; }
    @Override public LocalDate getExpirationDate() { return this.expirationDate; }
    @Override public LocalDate getReturnDate() { return this.returnDate; }

    @Override public void setBook(IBook book) { this.book = book; }
    @Override public void setUser(IUser user) { this.user= user; }
    @Override public void setLoanDate(LocalDate loanDate) {
        if (loanDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Loan date cannot be in the future.");
        }
        this.loanDate = loanDate;
    }
    @Override public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        updateState();
    }
    @Override public void setReturnDate(LocalDate returnDate) {
        if (isReturned()) {
            throw new IllegalStateException("Book has already been returned.");
        }
        this.returnDate = returnDate;
        updateState();
    }

    @Override public boolean isNull() { return false; }



    @Override public LoanState getState() {
        if (state == null) {
            updateState();
        }
        return state;
    }

    private void updateState() {
        if (returnDate != null) {
            state = new ReturnedState();
        } else if (LocalDate.now().isAfter(expirationDate)) {
            state = new ExpiredState();
        } else {
            state = new InProgressState();
        }
    }

    @Override public boolean isReturned() { return getState().isReturned(); }

    @Override public boolean isExpired() { return getState().isExpired(expirationDate); }

    @Override public boolean isInProgress() { return getState().isInProgress(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id;
    }

    @Override
    public String toString() {
        return "Loan {" +
                "Book = '" + book.toString() + '\'' +
                ", User = '" + user.toString() + '\'' +
                ", Loan Date = '" + loanDate + '\'' +
                ", Expiration Date = '" + expirationDate + '\'' +
                ", Return Date = '" + returnDate + '\'' +
                ", State = '" + state.getName() + '\'' +
                "}";
    }
}