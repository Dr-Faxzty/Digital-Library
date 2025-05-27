package persistence;

import model.Loan;


public class JsonLoanManager extends JsonTemplateManager<Loan> {
    private static JsonLoanManager JLoanManagerInstance;

    public JsonLoanManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\database\\loans.json", Loan[].class);
    }

    public static JsonLoanManager getInstance() {
        if (JLoanManagerInstance == null) {
            JLoanManagerInstance = new JsonLoanManager();
        }
        return JLoanManagerInstance;
    }
}
