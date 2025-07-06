package it.yellowradiators.controller;

import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.interfaces.ILoan;
import it.yellowradiators.common.interfaces.IUser;
import it.yellowradiators.manager.BookManager;
import it.yellowradiators.manager.LoanManager;
import it.yellowradiators.model.Book;
import it.yellowradiators.model.Loan;
import it.yellowradiators.persistence.JsonBookManager;
import it.yellowradiators.persistence.JsonLoanManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LoanController {
    private static LoanController LoanInstance;
    private final LoanManager loanManager;
    private final BookManager bookManager;
    private final JsonLoanManager jsonLoanManager;
    private final JsonBookManager jsonBookManager;

    private LoanController() {
        this.loanManager = LoanManager.getInstance();
        this.bookManager = BookManager.getInstance();
        this.jsonLoanManager = JsonLoanManager.getInstance();
        this.jsonBookManager = JsonBookManager.getInstance();

        List<ILoan> loans = loadLoans();
        loanManager.setInitialLoans(loans);
    }

    public static LoanController getInstance() {
        if (LoanInstance == null) {
            LoanInstance = new LoanController();
        }
        return LoanInstance;
    }

    public ILoan loanBook(IBook book, IUser user, LocalDate expirationDate) {
        ILoan loan = loanManager.loanBook(book, user, expirationDate);
        saveAll();
        return loan;
    }

    public boolean returnBook(ILoan loan) {
        boolean result = loanManager.returnBook(loan);
        if (result) saveAll();
        return result;
    }

    public boolean removeLoanById(int id) {
        boolean removed = loanManager.removeLoanById(id);
        if (removed) saveAll();
        return removed;
    }

    public List<ILoan> getAllLoans() {
        return loanManager.getAll();
    }

    public List<ILoan> searchLoans(Predicate<ILoan> filter) {
        return loanManager.search(filter);
    }

    private List<ILoan> loadLoans() {
        List<Loan> concreteBooks = jsonLoanManager.load();
        return new ArrayList<>(concreteBooks);
    }

    private void saveAll() {
        List<Loan> loans = loanManager.getAll().stream()
                .map(loan -> (Loan) loan)
                .toList();
        List<Book> books = bookManager.getAll().stream()
                .map(book -> (Book) book)
                .toList();

        jsonLoanManager.save(loans);
        jsonBookManager.save(books);
    }

    public void loadLoansAsync(java.util.function.Consumer<List<ILoan>> onSuccess, Runnable onError) {
        jsonLoanManager.loadAsync(loans -> {
            List<ILoan> iloans = loans.stream()
                    .map(loan -> (ILoan) loan)
                    .toList();
            loanManager.setInitialLoans(iloans);
            onSuccess.accept(iloans);
        }, onError);
    }

}
