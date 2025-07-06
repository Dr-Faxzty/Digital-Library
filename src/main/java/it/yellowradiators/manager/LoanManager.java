package it.yellowradiators.manager;

import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.interfaces.ILoan;
import it.yellowradiators.common.interfaces.IUser;
import it.yellowradiators.common.interfaces.Manager;
import it.yellowradiators.model.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LoanManager implements Manager<ILoan> {
    private static LoanManager instance;
    private final List<ILoan> loans = new ArrayList<>();
    private int nextId = 1;

    public LoanManager() {}

    public static LoanManager getInstance() {
        if (instance == null) {
            instance = new LoanManager();
        }
        return instance;
    }

    public void setInitialLoans(List<ILoan> initialLoans) {
        loans.clear();
        loans.addAll(initialLoans);
        nextId = loans.stream()
                .mapToInt(ILoan::getId)
                .max()
                .orElse(0) + 1;
    }

    public ILoan loanBook(IBook book, IUser user, LocalDate expirationDate){
        ILoan loan = new Loan(nextId++, book, user, LocalDate.now(), expirationDate);
        loans.add(loan);
        book.setAvailable(false);
        return loan;
    }

    public boolean returnBook(ILoan loan){
        if (loan.isReturned()) return false;

        loan.setReturnDate(LocalDate.now());
        loan.getBook().setAvailable(true);
        return true;
    }

    public boolean removeLoanById(int id) {
        return loans.removeIf(loan -> loan.getId() == id);
    }

    @Override
    public List<ILoan> search(Predicate<ILoan> filter) {
        return loans.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public List<ILoan> getAll() { return new ArrayList<>(loans); }
}
