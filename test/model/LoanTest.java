package model;

import common.enums.Role;
import common.interfaces.IBook;
import common.interfaces.IUser;
import common.state.ExpiredState;
import common.state.InProgressState;
import common.state.ReturnedState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private IBook dummyBook;
    private IUser dummyUser;

    @BeforeEach
    void setUp() {
        dummyBook = new IBook() {
            private String title = "Dummy Book";

            public String getIsbn() { return "123"; }
            public String getTitle() { return title; }
            public String getAuthor() { return "Author"; }
            public LocalDate getDate() { return LocalDate.now(); }
            public String getType() { return "Fiction"; }
            public boolean available() { return true; }
            public String getUrlImage() { return "http://example.com/image.jpg"; }

            public void setIsbn(String isbn) {}
            public void setTitle(String t) { this.title = t; }
            public void setAuthor(String a) {}
            public void setDate(LocalDate d) {}
            public void setType(String t) {}
            public void setAvailable(boolean b) {}
            public void setUrlImage(String u) {}

            public boolean isNull() { return false; }
        };


        dummyUser = new IUser() {
            public String getName() { return "Test"; }
            public String getSurname() { return "User"; }
            public String getTaxIdCode() { return "ABCDEF12G34H567I"; }
            public String getEmail() { return "test@example.com"; }
            public String getPassword() { return "password"; }
            public Role getRole() { return Role.USER; }

            public void setName(String name) {}
            public void setSurname(String surname) {}
            public void setTaxIdCode(String taxIdCode) {}
            public void setEmail(String email) {}
            public void setPassword(String password) {}
            public void setRole(Role role) {}

            public boolean isNull() { return false; }
        };

    }

    @Test
    void testConstructorAndGetters() {
        LocalDate loanDate = LocalDate.now().minusDays(5);
        LocalDate expirationDate = LocalDate.now().plusDays(5);
        Loan loan = new Loan(1, dummyBook, dummyUser, loanDate, expirationDate);

        assertEquals(1, loan.getId());
        assertEquals(dummyBook, loan.getBook());
        assertEquals(dummyUser, loan.getUser());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(expirationDate, loan.getExpirationDate());
        assertNull(loan.getReturnDate());
        assertInstanceOf(InProgressState.class, loan.getState());
    }

    @Test
    void testReturnedState() {
        Loan loan = new Loan(2, dummyBook, dummyUser, LocalDate.now().minusDays(10), LocalDate.now().plusDays(2));
        loan.setReturnDate(LocalDate.now());

        assertTrue(loan.isReturned());
        assertInstanceOf(ReturnedState.class, loan.getState());
    }

    @Test
    void testExpiredState() {
        Loan loan = new Loan(3, dummyBook, dummyUser, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1));

        assertTrue(loan.isExpired());
        assertInstanceOf(ExpiredState.class, loan.getState());
    }

    @Test
    void testIllegalReturnTwice() {
        Loan loan = new Loan(4, dummyBook, dummyUser, LocalDate.now().minusDays(5), LocalDate.now().plusDays(2));
        loan.setReturnDate(LocalDate.now());

        assertThrows(IllegalStateException.class, () -> {
            loan.setReturnDate(LocalDate.now());
        });
    }

    @Test
    void testIllegalFutureLoanDate() {
        Loan loan = new Loan();
        assertThrows(IllegalArgumentException.class, () -> {
            loan.setLoanDate(LocalDate.now().plusDays(3));
        });
    }

    @Test
    void testIsNullReturnsFalse() {
        Loan loan = new Loan();
        assertFalse(loan.isNull());
    }

    @Test
    void testToString() {
        Loan loan = new Loan(5, dummyBook, dummyUser, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5));
        String output = loan.toString();

        assertNotNull(output);
        assertTrue(output.startsWith("Loan {"));
    }
}
