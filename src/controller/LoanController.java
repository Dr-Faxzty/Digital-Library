package controller;

import model.Book;
import model.Loan;
import model.User;
import manager.LoanManager;
import manager.BookManager;
import persistence.JsonLoanManager;
import persistence.JsonBookManager;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class LoanController {
    private final LoanManager loanManager;
    private final BookManager bookManager;
    private final JsonLoanManager jsonLoanManager;
    private final JsonBookManager jsonBookManager;

    public LoanController() {
        this.loanManager = LoanManager.getInstance();
        this.bookManager = BookManager.getInstance();
        this.jsonLoanManager = new JsonLoanManager();
        this.jsonBookManager = new JsonBookManager();

        List<Loan> loans = loadLoans();
        loanManager.setInitialLoans(loans);
    }

    public Loan loanBook(Book book, User user, LocalDate expirationDate) {
        Loan loan = loanManager.loanBook(book, user, expirationDate);
        saveAll();
        return loan;
    }

    public boolean returnBook(Loan loan) {
        boolean result = loanManager.returnBook(loan);
        if (result) saveAll();
        return result;
    }

    public boolean removeLoanById(int id) {
        boolean removed = loanManager.removeLoanById(id);
        if (removed) saveAll();
        return removed;
    }

    public List<Loan> getAllLoans() {
        return loanManager.getAll();
    }

    public List<Loan> searchLoans(Predicate<Loan> filter) {
        return loanManager.search(filter);
    }

    private List<Loan> loadLoans() {
        return jsonLoanManager.load();
    }

    private void saveAll() {
        jsonLoanManager.save(loanManager.getAll());
        jsonBookManager.save(bookManager.getAll());
    }

    public void loadLoansAsync(java.util.function.Consumer<List<Loan>> onSuccess, Runnable onError) {
        jsonLoanManager.loadAsync(loans -> {
            loanManager.setInitialLoans(loans);
            onSuccess.accept(loans);
        }, onError);
    }

}
