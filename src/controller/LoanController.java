package controller;

import manager.LoanManager;
import manager.BookManager;
import persistence.JsonLoanManager;
import persistence.JsonBookManager;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import common.interfaces.ILoan;
import common.interfaces.IBook;
import common.interfaces.IUser;

public class LoanController {
    private static LoanController LoanInstance;
    private final LoanManager loanManager;
    private final BookManager bookManager;
    private final JsonLoanManager jsonLoanManager;
    private final JsonBookManager jsonBookManager;

    private LoanController() {
        this.loanManager = LoanManager.getInstance();
        this.bookManager = BookManager.getInstance();
        this.jsonLoanManager = new JsonLoanManager();
        this.jsonBookManager = new JsonBookManager();

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
        return jsonLoanManager.load();
    }

    private void saveAll() {
        jsonLoanManager.save(loanManager.getAll());
        jsonBookManager.save(bookManager.getAll());
    }

    public void loadLoansAsync(java.util.function.Consumer<List<ILoan>> onSuccess, Runnable onError) {
        jsonLoanManager.loadAsync(loans -> {
            loanManager.setInitialLoans(loans);
            onSuccess.accept(loans);
        }, onError);
    }

}
