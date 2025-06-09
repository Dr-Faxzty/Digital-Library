package view.user.components;

import common.interfaces.ILoan;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.user.BookDetailView;

public class LoanCard extends VBox {
    public LoanCard(ILoan loan, String statusTitle, String statusColor) {
        getStyleClass().add("loan-card");
        setAlignment(Pos.TOP_LEFT);
        setSpacing(6);

        getChildren().addAll(CreateTitle(loan.getBook().getTitle()), ObtainDueDate(loan),
                AssessmentStatus(loan, statusTitle, statusColor));

        setOnMouseClicked(e -> new BookDetailView().show((Stage) getScene().getWindow(), loan.getBook()));
    }

    private Label CreateTitle(String Title){
        Label title = new Label(Title);
        title.getStyleClass().add("loan-title");

        return title;
    }

    private Label ObtainDueDate(ILoan loan){
        Label dueDate = new Label(
                loan.isReturned() ?
                        "Returned on: " + loan.getReturnDate() :
                        "Due: " + loan.getExpirationDate()
        );
        dueDate.getStyleClass().add("loan-date");

        return dueDate;
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