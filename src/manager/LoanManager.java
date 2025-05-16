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

public class LoanManager implements Manager<Loan> {
    private static LoanManager instance;
    private final List<Loan> loans = new ArrayList<>();
    private int nextId = 1;

    public LoanManager() {}

    public static LoanManager getInstance() {
        if (instance == null) {
            instance = new LoanManager();
        }
        return instance;
    }

    public void setInitialLoans(List<Loan> initialLoans) {
        loans.clear();
        loans.addAll(initialLoans);
        nextId = loans.stream().mapToInt(Loan::getId).max().orElse(0) + 1;
    }

    public Loan loanBook(Book book, User user, LocalDate expirationDate){
        Loan loan = new Loan(nextId++, book, user, LocalDate.now(), expirationDate);
        loans.add(loan);
        book.setAvailable(false);
        return loan;
    }

    public boolean returnBook(Loan loan){
        if (loan.isReturned()) return false;

        loan.setReturnDate(LocalDate.now());
        loan.getBook().setAvailable(true);
        return true;
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
