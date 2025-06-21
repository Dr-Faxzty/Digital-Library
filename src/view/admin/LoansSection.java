package view.admin;

import controller.LoanController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import common.interfaces.ILoan;

import java.util.List;
import java.util.function.Function;

public class LoansSection extends VBox {
    private final LoanController loanController = LoanController.getInstance();
    private final TableView<ILoan> table = new TableView<>();
    private final ComboBox<String> statusFilter = new ComboBox<>();
    private final ProgressIndicator loadingSpinner = new ProgressIndicator();

    public LoansSection() {
        initializeLayout();
        setupTableColumns();
        loadData();
    }

    private void initializeLayout() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminBooks-style-1");

        configureSpinner();
        getChildren().addAll(createTitleBar(), createTopBar(), loadingSpinner, table);
    }

    private void configureSpinner() {
        loadingSpinner.setVisible(false);
        loadingSpinner.setManaged(false);
        loadingSpinner.setPrefSize(50, 50);
        loadingSpinner.setStyle("-fx-progress-color: #34A853;");
        VBox.setMargin(loadingSpinner, new Insets(16, 0, 0, 0));
        setAlignment(Pos.TOP_CENTER);
    }

    private void showLoadingSpinner(boolean show) {
        loadingSpinner.setVisible(show);
        loadingSpinner.setManaged(show);
    }

    private HBox createTitleBar() {
        Label title = new Label("Manage Loans");
        title.getStyleClass().add("adminBooks-style-2-1");

        HBox titleBar = new HBox(title);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(60);
        titleBar.setMaxWidth(Double.MAX_VALUE);
        titleBar.getStyleClass().add("adminBooks-style-2");

        return titleBar;
    }

    private HBox createTopBar() {
        statusFilter.getItems().addAll("All", "In Progress", "Expired", "Returned");
        statusFilter.setValue("All");
        statusFilter.getStyleClass().add("adminBooks-category-combo");
        statusFilter.setOnAction(e -> refreshTable());

        HBox topBar = new HBox(10, statusFilter);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        return topBar;
    }

    private void setupTableColumns() {
        table.getStyleClass().add("adminBooks-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No loans found"));
        VBox.setMargin(table, new Insets(16, 24, 24, 24));

        table.getColumns().addAll(
                createColumn("Book", loan -> loan.getBook().getTitle()),
                createColumn("User", loan -> loan.getUser().getName() + " " + loan.getUser().getSurname()),
                createColumn("Due Date", loan -> loan.getExpirationDate().toString()),
                createColumn("Status", loan -> loan.getState().getName())
        );
    }

    private TableColumn<ILoan, String> createColumn(String name, Function<ILoan, String> mapper) {
        TableColumn<ILoan, String> column = new TableColumn<>(name);
        column.setCellValueFactory(data -> new SimpleStringProperty(mapper.apply(data.getValue())));
        column.setCellFactory(col -> new TableCell<>() {
            private final Label label = new Label();
            private final HBox container = new HBox(label);

            {
                container.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item == null ? null : container);
                if (!empty && item != null) label.setText(item);
            }
        });
        return column;
    }

    private void loadData() {
        setControlsDisabled(true);
        showLoadingSpinner(true);

        loanController.loadLoansAsync(
                loans -> {
                    updateTableWithFilter(loans);
                    setControlsDisabled(false);
                    showLoadingSpinner(false);
                },
                () -> {
                    showError("Failed to load loans.");
                    setControlsDisabled(false);
                    showLoadingSpinner(false);
                }
        );
    }

    private void refreshTable() {
        updateTableWithFilter(loanController.getAllLoans());
    }

    private void updateTableWithFilter(List<ILoan> allLoans) {
        String selected = statusFilter.getValue();

        List<ILoan> filtered = switch (selected) {
            case "In Progress" -> allLoans.stream().filter(ILoan::isInProgress).toList();
            case "Expired"     -> allLoans.stream().filter(ILoan::isExpired).toList();
            case "Returned"    -> allLoans.stream().filter(ILoan::isReturned).toList();
            default             -> allLoans;
        };

        table.getItems().setAll(filtered);
    }

    private void setControlsDisabled(boolean disable) {
        statusFilter.setDisable(disable);
        table.setDisable(disable);
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}