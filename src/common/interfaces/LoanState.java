package common.interfaces;


import java.time.LocalDate;

public interface LoanState {
    boolean isReturned();
    boolean isExpired(LocalDate expirationDate);
    boolean isInProgress();
    String getName();
}