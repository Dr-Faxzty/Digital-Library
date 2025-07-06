package it.yellowradiators.view.user.components;

import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.interfaces.ILoan;
import it.yellowradiators.common.interfaces.IUser;
import it.yellowradiators.common.nullObject.NullLoan;
import it.yellowradiators.controller.LoanController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import it.yellowradiators.manager.SessionManager;
import it.yellowradiators.view.user.UserView;

import java.time.LocalDate;

public class HandleLoan {

    private static final LoanController loanController = LoanController.getInstance();

    public static void configure(Button loanButton, IBook book) {
        IUser user = SessionManager.getInstance().getLoggedUser();

        ILoan existingLoan = loanController.searchLoans(l ->
                l.getBook().equals(book) && l.getUser().equals(user) && l.isInProgress()
        ).stream().findFirst().orElse(new NullLoan());

        if (book.available()) {
            configureAsLoan(loanButton, book, user);
        } else if (!existingLoan.isNull()) {
            configureAsReturn(loanButton, book, existingLoan);
        } else {
            configureAsDisabled(loanButton);
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
