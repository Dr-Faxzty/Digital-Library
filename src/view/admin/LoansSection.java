package view.admin;

import controller.LoanController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Loan;

import java.util.List;

public class LoansSection extends VBox {
    private final LoanController loanController;
    private final TableView<Loan> table;
    private final ComboBox<String> statusFilter;
    private final ProgressIndicator loadingSpinner;

    public LoansSection() {
        this.loanController = new LoanController();
        this.table = new TableView<>();
        this.statusFilter = new ComboBox<>();
        this.loadingSpinner = new ProgressIndicator();

        styleContainer();
        configureSpinner();

        getChildren().addAll(createTitle(), createTopBar(), loadingSpinner, table);
        setupTableColumns();
        loadData();
    }

    private void styleContainer() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminBooks-style-1");
    }

    private void configureSpinner() {
        showLoadingSpinner(false);
        loadingSpinner.setPrefSize(50, 50);
        loadingSpinner.setStyle("-fx-progress-color: #34A853;");
        setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(loadingSpinner, new Insets(16, 0, 0, 0));
    }

    private void showLoadingSpinner(boolean show) {
        loadingSpinner.setVisible(show);
        loadingSpinner.setManaged(show);
    }

    private HBox createTitle() {
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(60);
        titleBar.setMaxWidth(Double.MAX_VALUE);
        titleBar.getStyleClass().add("adminBooks-style-2");

        Label title = new Label("Manage Loans");
        title.getStyleClass().add("adminBooks-style-2-1");
        titleBar.getChildren().add(title);
        return titleBar;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        statusFilter.getItems().addAll("All", "In Progress", "Expired", "Returned");
        statusFilter.setValue("All");
        statusFilter.getStyleClass().add("adminBooks-category-combo");
        statusFilter.setOnAction(e -> refreshTable());

        topBar.getChildren().addAll(statusFilter);
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
                createColumn("Status", this::getLoanStatus)
        );
    }

    private TableColumn<Loan, String> createColumn(String name, java.util.function.Function<Loan, String> mapper) {
        TableColumn<Loan, String> column = new TableColumn<>(name);

        column.setCellValueFactory(data -> new SimpleStringProperty(mapper.apply(data.getValue())));

        column.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox();
            private final Label label = new Label();

            {
                container.setAlignment(Pos.CENTER);
                container.getChildren().add(label);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(container);
                }
            }
        });

        return column;
    }

    private String getLoanStatus(Loan loan) {
        return loan.getState().getName();
    }

    private void loadData() {
        setControlsDisabled(true);
        showLoadingSpinner(true);

        loanController.loadLoansAsync(allLoans -> {
            updateTableWithFilter(allLoans);
            showLoadingSpinner(false);
            setControlsDisabled(false);
        }, () -> {
            showError("Failed to load loans.");
            showLoadingSpinner(false);
            setControlsDisabled(false);
        });
    }

    private void refreshTable() {
        List<Loan> allLoans = loanController.getAllLoans();
        updateTableWithFilter(allLoans);
    }

    private void updateTableWithFilter(List<Loan> allLoans) {
        String selected = statusFilter.getValue();

        List<Loan> filtered = switch (selected) {
            case "In Progress" -> allLoans.stream().filter(Loan::isInProgress).toList();
            case "Expired"     -> allLoans.stream().filter(Loan::isExpired).toList();
            case "Returned"    -> allLoans.stream().filter(Loan::isReturned).toList();
            default            -> allLoans;
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
