package view.admin;

import controller.UserController;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import common.interfaces.IUser;
import javafx.util.Duration;

import java.util.List;

public class UsersSection extends VBox {
    private final UserController userController;
    private final TextField searchField;
    private final TableView<IUser> table;
    private final Button searchButton;
    private final ProgressIndicator loadingSpinner;
    private final PauseTransition debounce;

    public UsersSection() {
        this.userController = UserController.getInstance();
        this.searchField = new TextField();
        this.table = new TableView<>();
        this.searchButton = new Button("🔍 Search");
        this.loadingSpinner = new ProgressIndicator();
        this.debounce = new PauseTransition(Duration.millis(300));

        styleContainer();
        configureLoadingSpinner();
        getChildren().addAll(createTitle(), createTopBar(), loadingSpinner, table);

        configureSearchDebounce();
        setupTableColumns();
        refreshTable();
    }

    private void styleContainer() {
        setMaxWidth(Double.MAX_VALUE);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("adminUsers-style-1");
    }

    private void configureLoadingSpinner() {
        showLoadingSpinner(false);
        loadingSpinner.setPrefSize(40, 40);
        loadingSpinner.setStyle("-fx-progress-color: #34A853;");
        VBox.setMargin(loadingSpinner, new Insets(10));
    }

    private void showLoadingSpinner(boolean show) {
        loadingSpinner.setVisible(show);
        loadingSpinner.setManaged(show);
    }

    private HBox createTitle() {
        Label title = new Label("Manage Users");
        title.getStyleClass().add("adminUsers-style-2-1");

        HBox titleBar = new HBox(title);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(60);
        titleBar.setMaxWidth(Double.MAX_VALUE);
        titleBar.getStyleClass().add("adminUsers-style-2");

        return titleBar;
    }

    private HBox createTopBar() {
        searchField.setPromptText("Search users...");
        searchField.setPrefWidth(200);
        searchField.getStyleClass().add("adminUsers-style-2-2");
        searchField.textProperty().addListener((obs, oldText, newText) -> debounce.playFromStart());

        searchButton.getStyleClass().add("adminUsers-style-3");
        searchButton.setOnAction(e -> refreshTable());

        HBox topBar = new HBox(10, searchField, searchButton);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        return topBar;
    }

    private void configureSearchDebounce() {
        debounce.setOnFinished(e -> refreshTable());
    }

    private void setupTableColumns() {
        table.getColumns().addAll(
                createColumn("Tax Id Code", IUser::getTaxIdCode),
                createColumn("User", user -> user.getName() + " " + user.getSurname()),
                createColumn("Email", IUser::getEmail),
                createColumn("Role", user -> user.getRole().getLabel()),
                createActionsColumn()
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label(""));
        table.getStyleClass().add("adminUsers-table");
        VBox.setMargin(table, new Insets(16, 24, 24, 24));
    }

    private TableColumn<IUser, String> createColumn(String name, java.util.function.Function<IUser, String> mapper) {
        TableColumn<IUser, String> column = new TableColumn<>(name);

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


    private TableColumn<IUser, Void> createActionsColumn() {
        TableColumn<IUser, Void> column = new TableColumn<>("Actions");

        column.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox();
            private final Button delete = new Button("🗑");

            {
                container.setAlignment(Pos.CENTER);
                delete.getStyleClass().add("adminUsers-style-5");

                delete.setOnMouseClicked(e -> {
                    IUser user = getTableView().getItems().get(getIndex());
                    boolean removed = userController.removeUser(user.getTaxIdCode());
                    if (removed) {
                        getTableView().getItems().remove(user);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Error while deleting User").show();
                    }
                });

                container.getChildren().add(delete);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });

        return column;
    }

    private void refreshTable() {
        setControlsDisabled(true);
        showLoadingSpinner(true);

        List<IUser> allUsers = userController.getAllUsers();
        List<IUser> filtered = filterUsers(allUsers, searchField.getText().toLowerCase());

        table.getItems().setAll(filtered);
        setControlsDisabled(false);
        showLoadingSpinner(false);
    }

    private List<IUser> filterUsers(List<IUser> users, String search) {
        if (search.isBlank()) return users;

        return users.stream()
                .filter(user ->
                        user.getName().toLowerCase().contains(search) ||
                                user.getSurname().toLowerCase().contains(search) ||
                                user.getTaxIdCode().toLowerCase().contains(search) ||
                                user.getEmail().toLowerCase().contains(search)
                )
                .toList();
    }

    private void setControlsDisabled(boolean disabled) {
        searchField.setDisable(disabled);
        searchButton.setDisable(disabled);
        table.setDisable(disabled);
    }
}
