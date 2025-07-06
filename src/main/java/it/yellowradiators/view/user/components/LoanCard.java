package it.yellowradiators.view.user.components;

import it.yellowradiators.common.interfaces.ILoan;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.yellowradiators.view.user.BookDetailView;

public class LoanCard extends VBox {
    public LoanCard(ILoan loan, String statusTitle, String statusColor) {
        getStyleClass().add("loan-card");
        setAlignment(Pos.TOP_LEFT);
        setSpacing(6);

        getChildren().addAll(CreateLabel(loan.getBook().getTitle(), "loan-title"), CreateLabel((loan.isReturned() ?
                        "Returned on: " + loan.getReturnDate() :
                        "Due: " + loan.getExpirationDate()), "loan-date"),
                AssessmentStatus(loan, statusTitle, statusColor));

        setOnMouseClicked(e -> new BookDetailView().show((Stage) getScene().getWindow(), loan.getBook()));
    }

    private Label CreateLabel(String Title, String styleClasses) {
        Label label = new Label(Title);
        label.getStyleClass().add(styleClasses);

        return label;
    }

    private Label AssessmentStatus(ILoan loan, String statusTitle, String statusColor) {
        Label status = new Label("Status: " + statusTitle);
        status.setStyle("-fx-text-fill: " + statusColor);
        status.getStyleClass().add("loan-status");

        if (loan.isExpired()) {
            status.setText(status.getText() + " ⚠");
            Tooltip tip = new Tooltip("This loan has expired. Please return the book.");
            Tooltip.install(this, tip);
            getStyleClass().add("expired");
        }

        return status;
    }
}