package model;

import java.time.LocalDate;

public class Loan{
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate deadlineDate;
    private LocalDate loanRepayment;

    public Loan(){}

    public Loan(Book book, User user, LocalDate loanDate, int loanPeriodDays){
        this.id = id;
        this.idLibro = idLibro;
        this.taxIdCodeUser = taxIdCodeUser;
        this.loanDate = loanDate;
        this.deadlineDate = loanDate.plusDays(loanPeriodDays);
        this.loanRepayment = null;
    }

    public Book getBook(){return this.book;}
    public User getUser(){return this.user;}
    public LocalDate getLoanDate(){return this.loanDate;}
    public LocalDate getDeadlineDate(){return this.deadlineDate;}
    public LocalDate getLoanRepayment(){return this.loanRepayment;}

    public void setBook(Book book){this.book = book;}
    public void setUser(User user){this.user= user;}
    public void setLoanDate(LocalDate loanDate){this.loanDate = loanDate;}
    public void setDeadlineDate(LocalDate deadlineDate){this.deadlineDate = deadlineDate;}
    public void setLoanRepayment(LocalDate loanRepayment){this.loanRepayment = loanRepayment;}

    public boolean isReturned(){ return loanRepayment != null; }

    public boolean isOverdue(){ return !isReturned() && LocalDate.now().isAfter(deadlineDate); }

    public void DateReturnBook(){ this.returnDate = LocalDate.now(); }

    @Override
    public String toString(){
        return "Loan {" +
                "Book = '" + book.toString() + '\'' +
                ", User = '" + user.toString() + '\'' +
                ", Loan Data = '" + loanDate + '\'' +
                ", Deadline Date = '" + deadlineDate + '\'' +
                ", Loan Repayment = '" + loanRepayment + '\'' +
                "}";
    }

}