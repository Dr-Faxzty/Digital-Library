package manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import common.interfaces.Manager;
import model.Book;
import model.Loan;
import model.User;
import persistence.JsonLoanManager;

public class LoanManager implements Manager<Loan> {
    private static LoanManager instance;
    private final List<Loan> loans = new ArrayList<>();
    private int nextId = 1;

    public LoanManager() {
        JsonLoanManager jsonLoanManager = new JsonLoanManager();
        List<Loan> initialLoans = jsonLoanManager.load();
        loans.addAll(initialLoans);
    }

    public static LoanManager getInstance() {
        if (instance == null) {
            instance = new LoanManager();
        }
        return instance;
    }

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

    public boolean removeLoanById(int id) {
        return loans.removeIf(loan -> loan.getId() == id);
    }

    @Override
    public List<Loan> search(Predicate<Loan> filter) {
        return loans.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> getAll() { return new ArrayList<>(loans); }
}