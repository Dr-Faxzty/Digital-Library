package model;

import java.time.LocalDate;

public class Loan{
    private final int id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate expirationDate;
    private LocalDate returnDate;

    public Loan() { this.id = 0; }

    public Loan(int id, Book book, User user, LocalDate loanDate, LocalDate expirationDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.expirationDate = expirationDate;
        this.returnDate = null;
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
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public void setReturnDate(LocalDate returnDate) { 
        if (getLoanState() == LoanState.RETURNED) {
            throw new IllegalStateException("Book has already been returned.");
        }
        this.returnDate = returnDate; 
    }

    public LoanState getLoanState() {
        if (returnDate != null) return LoanState.RETURNED;
        if (LocalDate.now().isAfter(expirationDate)) return LoanState.EXPIRED;
        return LoanState.IN_PROGRESS;
    }

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
                "}";
    }
}