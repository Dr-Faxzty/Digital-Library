package common.interfaces;

import java.time.LocalDate;
import common.state.LoanState;

public interface ILoan{
    int getId();
    IBook getBook();
    IUser getUser();
    LocalDate getLoanDate();
    LocalDate getExpirationDate();
    LocalDate getReturnDate();

    void setBook(IBook book);
    void setUser(IUser user);
    void setLoanDate(LocalDate loanDate);
    void setExpirationDate(LocalDate expirationDate);
    void setReturnDate(LocalDate returnDate);

    LoanState getState();

    boolean isReturned();
    boolean isExpired();
    boolean isInProgress();

    boolean isNull();
}
