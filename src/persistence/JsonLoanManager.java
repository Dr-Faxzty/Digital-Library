package persistence;

import common.interfaces.ILoan;


public class JsonLoanManager extends JsonTemplateManager<ILoan> {
    private static JsonLoanManager JLoanManagerInstance;

    public JsonLoanManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\Digital-Library\\database\\loans.json", ILoan[].class);
    }

    public static JsonLoanManager getInstance() {
        if (JLoanManagerInstance == null) {
            JLoanManagerInstance = new JsonLoanManager();
        }
        return JLoanManagerInstance;
    }
}
