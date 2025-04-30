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

    public List<Loan> getActiveLoansByUser(User user){
        return loans.stream() 
                .filter(loan -> !loan.isReturned() && loan.getUser().equals(user)) //Use of filter to take the loans that have not yet been returned
                .collect(Collectors.toList()); //Collects the results in a new object, of type Collectors, that is a list 
    }

    public List<Loan> getActiveLoansByBook(Book book){
        return loans.stream() 
                .filter(loan -> !loan.isReturned() && loan.getBook().equals(book)) //Use of filter to take the loans that have not yet been returned
                .collect(Collectors.toList()); //Collects the results in a new object, of type Collectors, that is a list 
    }

    public List<Loan> getOverdueLoans(){
        return loans.stream() 
                .filter(loan -> !loan.isReturned() && loan.isOverdue()) //Use of filter to take the loans that have not yet been returned
                .collect(Collectors.toList()); //Collects the results in a new object, of type Collectors, that is a list 
    }
}