package persistence;

import model.Loan;


public class JsonLoanManager extends JsonTemplateManager<Loan> {
    public JsonLoanManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\Digital-Library\\database\\loans.json", Loan[].class);
    }
}
