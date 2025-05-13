package persistence;

import model.Loan;


public class JsonLoanManager extends JsonTemplateManager<Loan> {
    public JsonLoanManager() {
        super("database/loans.json", Loan[].class);
    }
}
