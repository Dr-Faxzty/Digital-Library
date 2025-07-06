package it.yellowradiators.persistence;

import it.yellowradiators.model.Loan;


public class JsonLoanManager extends JsonTemplateManager<Loan> {
    private static JsonLoanManager JLoanManagerInstance;

    public JsonLoanManager() {
        super("database/loans.json", Loan[].class);
    }

    public static JsonLoanManager getInstance() {
        if (JLoanManagerInstance == null) {
            JLoanManagerInstance = new JsonLoanManager();
        }
        return JLoanManagerInstance;
    }
}
