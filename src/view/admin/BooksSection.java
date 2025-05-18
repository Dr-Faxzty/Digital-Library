package view.admin;

import common.enums.BookCategoryType;
import common.strategy.BookOrderType;
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
import common.nullObject.NullBook;
import model.User;
import utils.BookQueryUtils;
import view.admin.components.BookEditDialog;

import java.util.Arrays;
import java.util.List;

public class BooksSection extends VBox {
    private final BookController bookController;
    private final TextField searchField;
    private final TableView<Book> table;
    private final ComboBox<String> categoryCombo;
    private final ComboBox<String> orderCombo;


    public BooksSection() {
        this.bookController = new BookController();
        this.searchField = new TextField();
        this.table = new TableView<>();
        this.categoryCombo = new ComboBox<>();
        this.orderCombo = new ComboBox<>();

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

        categoryCombo.getItems().addAll(
                Arrays.stream(BookCategoryType.values())
                        .map(BookCategoryType::getLabel)
                        .toList()
        );
        categoryCombo.setValue("All");
        categoryCombo.getStyleClass().add("adminBooks-category-combo");
        categoryCombo.setOnAction(e -> refreshTable());


        orderCombo.getItems().addAll(
                Arrays.stream(BookOrderType.values())
                        .map(BookOrderType::getLabel)
                        .toList()
        );
        orderCombo.setValue(BookOrderType.TITLE_A_Z.getLabel());
        orderCombo.getStyleClass().add("adminBooks-category-combo");
        orderCombo.setOnAction(e -> refreshTable());


        searchField.setPromptText("Search books...");
        searchField.setPrefWidth(200);
        searchField.getStyleClass().add("adminBooks-style-2-2");
        searchField.textProperty().addListener((obs, oldText, newText) -> refreshTable());

        Button searchButton = new Button("🔍 Search");
        searchButton.getStyleClass().add("adminBooks-style-3");
        searchButton.setOnAction(e -> refreshTable());


        Button addBookButton = new Button("+ Add Book");
        addBookButton.getStyleClass().add("adminBooks-style-4");
        addBookButton.setOnAction(e -> {
            new BookEditDialog().show(
                    (Stage) getScene().getWindow(),
                    new NullBook(),
                    () -> {
                        getChildren().removeIf(node -> node instanceof TableView);
                        getChildren().add(createTable());
                    }
            );
        });


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(categoryCombo, orderCombo, spacer, searchField, searchButton, addBookButton);
        return topBar;
    }

    private void refreshTable() {
        List<Book> allBooks = bookController.getAllBooks();

        BookCategoryType categoryType = BookCategoryType.fromLabel(categoryCombo.getValue());
        BookOrderType orderType = BookOrderType.fromLabel(orderCombo.getValue());

        List<Book> filtered = BookQueryUtils.getFilteredBooks(allBooks, categoryType, orderType);

        String search = searchField.getText().toLowerCase();
        if (!search.isBlank()) {
            filtered = filtered.stream()
                    .filter(book ->
                            search.contains(book.getTitle().toLowerCase()) ||
                            search.contains(book.getAuthor().toLowerCase())
                    )
                    .toList();
        }

        table.getItems().setAll(filtered);
    }





    private TableView<Book> createTable() {
        table.getStyleClass().add("adminBooks-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
        table.setPlaceholder(new Label(""));
        VBox.setMargin(table, new Insets(16, 24, 24, 24));

        table.getColumns().addAll(
                createCoverColumn(),
                createColumn("Author", Book::getAuthor),
                createColumn("Category", Book::getType),
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

    private TableColumn<Book, String> createRatingColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Evaluation");

        column.setCellValueFactory(data -> new SimpleStringProperty("4.5"));

        column.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox();
            private final Label label = new Label("4.5 / 5");

            {
                container.setAlignment(Pos.CENTER);
                label.getStyleClass().add("adminBooks-style-5");
                container.getChildren().add(label);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
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
                    new BookEditDialog().show(
                        (Stage) getScene().getWindow(),
                        book,
                        () -> {
                            getTableView().getItems().clear();
                            getTableView().getItems().addAll(bookController.getAllBooks());
                        }
                    );
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
