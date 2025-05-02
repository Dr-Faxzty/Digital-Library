package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanManager{
    private List<Loan> loans = new ArrayList<>();
    private int nextId = 1;

    public Loan loanBook(Book book, User user, LocalDate expirationDate){
        Loan loan = new Loan(nextId++, book, user, LocalDate.now(), expirationDate);
        loans.add(loan);
        return loan;
    }

    public boolean returnBook(Loan loan){
        if(!loan.isReturned()){
            loan.setReturnDate(LocalDate.now());
            return true;
        }
        return false;
    }

    public List<Loan> getActiveLoans(){
        return loans.stream() 
                .filter(Loan::isInProgress)
                .collect(Collectors.toList());
    }

    public List<Loan> getActiveLoansByUser(User user){
        return loans.stream() 
                .filter(loan -> loan.isInProgress() && loan.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<Loan> getActiveLoansByBook(Book book){
        return loans.stream() 
                .filter(loan -> loan.isInProgress() && loan.getBook().equals(book))
                .collect(Collectors.toList());
    }

    public List<Loan> getExpiredLoans(){
        return loans.stream() 
                .filter(Loan::isExpired)
                .collect(Collectors.toList());
    }

    public List<Loan> getAllLoans() { return new ArrayList<>(loans); }

    public boolean removeLoanById(int id) {
        return loans.removeIf(loan -> loan.getId() == id);
    }
}