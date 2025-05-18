package view.admin;

import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.User;

import java.util.List;

public class UsersSection extends VBox {
    private final UserController userController;
    private final TextField searchField;
    private final TableView<User> table;

    public UsersSection() {
        this.userController = new UserController();
        this.searchField = new TextField();
        this.table = new TableView<>();

        styleContainer();
        getChildren().addAll(createTitle(), createTopBar(), createTable());
    }

    private void styleContainer() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminUsers-style-1");
    }

    private HBox createTitle() {
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(60);
        titleBar.setMaxWidth(Double.MAX_VALUE);
        titleBar.getStyleClass().add("adminUsers-style-2");

        Label title = new Label("Manage Users");
        title.getStyleClass().add("adminUsers-style-2-1");
        titleBar.getChildren().add(title);
        return titleBar;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        searchField.setPromptText("Search users...");
        searchField.setPrefWidth(200);
        searchField.getStyleClass().add("adminUsers-style-2-2");
        searchField.textProperty().addListener((obs, oldText, newText) -> refreshTable());

        Button searchButton = new Button("🔍 Search");
        searchButton.getStyleClass().add("adminUsers-style-3");
        searchButton.setOnAction(e -> refreshTable());

        topBar.getChildren().addAll(searchField, searchButton);
        return topBar;
    }

    private TableView<User> createTable() {
        table.getStyleClass().add("adminUsers-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label(""));
        VBox.setMargin(table, new Insets(16, 24, 24, 24));

        table.getColumns().addAll(
                createColumn("Tax Id Code", User::getTaxIdCode),
                createColumn("User", user -> user.getName() + " " + user.getSurname()),
                createColumn("Email", User::getEmail),
                createColumn("Role", user -> user.getRole().getLabel()),
                createActionsColumn()
        );

        table.getItems().addAll(userController.getAllUsers());
        return table;
    }

    private TableColumn<User, String> createColumn(String name, java.util.function.Function<User, String> mapper) {
        TableColumn<User, String> column = new TableColumn<>(name);

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
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(container);
                }
            }
        });

        return column;
    }


    private TableColumn<User, Void> createActionsColumn() {
        TableColumn<User, Void> column = new TableColumn<>("Actions");
        column.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox();
            private final Label delete = new Label("🗑");

            {
                container.setAlignment(Pos.CENTER);
                delete.getStyleClass().add("adminUsers-style-5");

                delete.setOnMouseClicked(e -> {
                    User user = getTableView().getItems().get(getIndex());
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
        List<User> allUsers = userController.getAllUsers();
        String search = searchField.getText().toLowerCase();

        if (!search.isBlank()) {
            List<User> filtered = allUsers.stream()
                    .filter(user ->
                            search.contains(user.getName().toLowerCase()) ||
                                    user.getName().toLowerCase().contains(search) ||
                                    user.getSurname().toLowerCase().contains(search) ||
                                    user.getTaxIdCode().toLowerCase().contains(search) ||
                                    user.getEmail().toLowerCase().contains(search)
                    )
                    .toList();

            table.getItems().setAll(filtered);
        }
    }
}
