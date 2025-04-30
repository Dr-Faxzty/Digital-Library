package model;

public class LoanManager{
    private List<Loan> loans = new ArrayList<>();

    public Loan loanBook(Book book, User user, int loanPeriodDays){
        Loan loan = new Loan(book, user, LocalDate.now(), loanPeriodDays);
        loans.add(loan);
        return loan;
    }

    public boolean returnBook(Loan loan){
        if(!loan.isReturned()){
            loan.DateReturnBook();
            return true;
        }
        return false;
    }

    public List<Loan> getActiveLoans(){
        return loans.stream() 
                .filter(loan -> !loan.isReturned()) //Use of filter to take the loans that have not yet been returned
                .collect(Collectors.toList()); //Collects the results in a new object, of type Collectors, that is a list 
    }
}