package it.yellowradiators.manager;

import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.interfaces.ILoan;
import it.yellowradiators.common.interfaces.IUser;
import it.yellowradiators.model.Book;
import it.yellowradiators.model.Loan;
import it.yellowradiators.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static it.yellowradiators.common.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

public class LoanManagerTest {

    private LoanManager manager;
    private IBook book;
    private IUser user;

    @BeforeEach
    void setUp() {
        manager = new LoanManager();
        book = new Book("111", "Clean Code", "Bob", LocalDate.of(2010, 1, 1), "Narrative", true, "img");
        user = new User("Mario", "Rossi", "RSSMRA00A01H501U", "mario@rossi.com", "pass", USER);
    }

    @Test
    void testSetInitialLoans() {
        ILoan loan = new Loan(5, book, user, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5));
        manager.setInitialLoans(List.of(loan));

        List<ILoan> all = manager.getAll();
        assertEquals(1, all.size());
        assertEquals(6, manager.loanBook(book, user, LocalDate.now().plusDays(10)).getId());
    }

    @Test
    void testLoanBookCreatesNewLoan() {
        ILoan loan = manager.loanBook(book, user, LocalDate.now().plusDays(7));

        assertNotNull(loan);
        assertEquals(1, loan.getId());
        assertFalse(book.available());
        assertEquals(1, manager.getAll().size());
    }

    @Test
    void testReturnBookWorks() {
        ILoan loan = manager.loanBook(book, user, LocalDate.now().plusDays(7));
        boolean returned = manager.returnBook(loan);

        assertTrue(returned);
        assertTrue(book.available());
        assertNotNull(loan.getReturnDate());
    }

    @Test
    void testReturnAlreadyReturnedBookFails() {
        ILoan loan = manager.loanBook(book, user, LocalDate.now().plusDays(7));
        manager.returnBook(loan);

        boolean secondReturn = manager.returnBook(loan);
        assertFalse(secondReturn);
    }

    @Test
    void testRemoveLoanById() {
        ILoan loan = manager.loanBook(book, user, LocalDate.now().plusDays(3));
        boolean removed = manager.removeLoanById(loan.getId());

        assertTrue(removed);
        assertTrue(manager.getAll().isEmpty());
    }

    @Test
    void testSearchLoanByUser() {
        ILoan loan = manager.loanBook(book, user, LocalDate.now().plusDays(7));
        List<ILoan> result = manager.search(l -> l.getUser().getEmail().equals("mario@rossi.com"));

        assertEquals(1, result.size());
        assertEquals("Mario", result.getFirst().getUser().getName());
    }
}
