package model;

import java.time.LocalDate;

import common.state.LoanState;
import common.state.ExpiredState;
import common.state.InProgressState;
import common.state.ReturnedState;

public class Loan{
    private final int id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate expirationDate;
    private LocalDate returnDate;
    private transient LoanState state;

    public Loan() { this.id = 0; }

    public Loan(int id, Book book, User user, LocalDate loanDate, LocalDate expirationDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.expirationDate = expirationDate;
        this.returnDate = null;
        updateState();
    }

    public int getId() { return this.id; }
    public Book getBook() { return this.book; }
    public User getUser() { return this.user; }
    public LocalDate getLoanDate() { return this.loanDate; }
    public LocalDate getExpirationDate() { return this.expirationDate; }
    public LocalDate getReturnDate() { return this.returnDate; }

    public void setBook(Book book) { this.book = book; }
    public void setUser(User user) { this.user= user; }
    public void setLoanDate(LocalDate loanDate) { 
        if (loanDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Loan date cannot be in the future.");
        }
        this.loanDate = loanDate; 
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        updateState();
    }
    public void setReturnDate(LocalDate returnDate) { 
        if (isReturned()) {
            throw new IllegalStateException("Book has already been returned.");
        }
        this.returnDate = returnDate;
        updateState();
    }

    public LoanState getState() {
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

    public boolean isReturned() { return getState().isReturned(); }

    public boolean isExpired() { return getState().isExpired(expirationDate); }

    public boolean isInProgress() { return getState().isInProgress(); }

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