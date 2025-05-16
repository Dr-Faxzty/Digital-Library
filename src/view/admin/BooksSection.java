package view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import model.Book;
import controller.BookController;
import view.admin.components.BookEditDialog;

public class BooksSection extends VBox {
    private final BookController bookController;

    public BooksSection() {
        this.bookController = new BookController();

        styleContainer();
        getChildren().addAll(createTitle(), createTopBar(), createTable());
    }

    private void styleContainer() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminBooks-style-1");
    }

    private HBox createTitle() {
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(60);
        titleBar.setMaxWidth(Double.MAX_VALUE);
        titleBar.getStyleClass().add("adminBooks-style-2");

        Label title = new Label("Manage Books");
        title.getStyleClass().add("adminBooks-style-2-1");
        titleBar.getChildren().add(title);

        return titleBar;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        TextField searchField = new TextField();
        searchField.setPromptText("Search books...");
        searchField.setPrefWidth(200);

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("adminBooks-style-3");

        Button addBookButton = new Button("+ Add Book");
        addBookButton.getStyleClass().add("adminBooks-style-4");

        topBar.getChildren().addAll(searchField, searchButton, addBookButton);
        return topBar;
    }

    private TableView<Book> createTable() {
        TableView<Book> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setMargin(table, new Insets(16, 24, 24, 24));

        table.getColumns().addAll(
                createCoverColumn(),
                createColumn("Author", book -> book.getAuthor()),
                createColumn("Category", book -> book.getType()),
                createColumn("Year", book -> String.valueOf(book.getDate().getYear())),
                createRatingColumn(),
                createActionsColumn()
        );

        table.getItems().addAll(bookController.getAllBooks());

        return table;
    }

    private TableColumn<Book, String> createCoverColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Title");
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        column.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox(10);
            private final ImageView imageView = new ImageView();
            private final Label titleLabel = new Label();

            {
                imageView.setFitWidth(32);
                imageView.setFitHeight(48);
                container.setAlignment(Pos.CENTER_LEFT);
                container.getChildren().addAll(imageView, titleLabel);
            }

            @Override
            protected void updateItem(String title, boolean empty) {
                super.updateItem(title, empty);
                if (empty || title == null) {
                    setGraphic(null);
                } else {
                    Book book = getTableView().getItems().get(getIndex());
                    try {
                        imageView.setImage(new Image(book.getUrlImage()));
                    } catch (Exception e) {
                        imageView.setImage(new Image("https://via.placeholder.com/32x48"));
                    }
                    titleLabel.setText(title);
                    setGraphic(container);
                }
            }
        });
        return column;
    }

    private TableColumn<Book, String> createColumn(String name, java.util.function.Function<Book, String> mapper) {
        TableColumn<Book, String> column = new TableColumn<>(name);
        column.setCellValueFactory(data -> new SimpleStringProperty(mapper.apply(data.getValue())));
        return column;
    }

    private TableColumn<Book, String> createRatingColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Evaluation");
        column.setCellValueFactory(data -> new SimpleStringProperty("4.5")); // Placeholder

        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    setText("4.5 / 5");
                    getStyleClass().add("adminBooks-style-5");
                }
            }
        });
        return column;
    }

    private TableColumn<Book, Void> createActionsColumn() {
        TableColumn<Book, Void> column = new TableColumn<>("Actions");
        column.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox(8);
            private final Label edit = new Label("✏");
            private final Label delete = new Label("🗑");

            {
                container.setAlignment(Pos.CENTER);
                edit.getStyleClass().add("adminBooks-style-6");
                delete.getStyleClass().add("adminBooks-style-7");

                edit.setOnMouseClicked(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    new BookEditDialog().show((Stage) getScene().getWindow(), book);
                    getTableView().refresh();
                });

                delete.setOnMouseClicked(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    boolean removed = bookController.removeBook(book.getIsbn());
                    if (removed) {
                        getTableView().getItems().remove(book);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting book.");
                        alert.showAndWait();
                    }
                });


                container.getChildren().addAll(edit, delete);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
        return column;
    }
}
