package it.yellowradiators.common.state;


import java.time.LocalDate;

public interface LoanState {
    boolean isReturned();
    boolean isExpired(LocalDate expirationDate);
    boolean isInProgress();
    String getName();
}