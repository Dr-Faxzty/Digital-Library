package view.user.components;

import common.interfaces.IBook;
import common.interfaces.ILoan;
import common.interfaces.IUser;
import common.nullObject.NullLoan;
import controller.LoanController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import manager.SessionManager;
import view.user.UserView;

import java.time.LocalDate;

public class HandleLoan {

    private static final LoanController loanController = LoanController.getInstance();

    public static void configure(Button loanButton, IBook book) {
        IUser user = SessionManager.getInstance().getLoggedUser();

        ILoan existingLoan = loanController.searchLoans(l ->
                l.getBook().equals(book) && l.getUser().equals(user) && l.isInProgress()
        ).stream().findFirst().orElse(new NullLoan());

        if (!book.available() && existingLoan.isNull()) {
            configureAsDisabled(loanButton);
        } else if (existingLoan.isNull()) {
            configureAsReturn(loanButton, book, existingLoan);
        } else {
            configureAsLoan(loanButton, book, user);
        }
    }

    private static void configureAsDisabled(Button loanButton) {
        loanButton.setDisable(true);
        loanButton.setText("✓ Already Loaned");
    }

    private static void configureAsReturn(Button loanButton, IBook book, ILoan existingLoan) {
        loanButton.setText("↩ Return Book");
        loanButton.setOnAction(e -> {
            loanController.returnBook(existingLoan);
            book.setAvailable(true);
            showAlert("Book returned successfully!");
            reloadUserView(loanButton);
        });
    }

    private static void configureAsLoan(Button loanButton, IBook book, IUser user) {
        loanButton.setText("📚 Loan this book");
        loanButton.setOnAction(e -> {
            loanController.loanBook(book, user, LocalDate.now().plusDays(14));
            showAlert("Loan registered successfully!");
            reloadUserView(loanButton);
        });
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void reloadUserView(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        new UserView().start(stage);
    }
}
