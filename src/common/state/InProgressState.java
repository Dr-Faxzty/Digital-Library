package common.state;

import java.time.LocalDate;

public class InProgressState implements LoanState {
    public boolean isReturned() { return false; }
    public boolean isExpired(LocalDate expirationDate) { return false; }
    public boolean isInProgress() { return true; }
    public String getName() { return "IN_PROGRESS"; }
}