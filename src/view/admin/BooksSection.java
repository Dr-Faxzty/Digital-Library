package view.admin;

import common.enums.BookCategoryType;
import common.strategy.BookOrderType;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import model.Book;
import controller.BookController;
import common.nullObject.NullBook;
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
    private final ProgressIndicator loadingSpinner;
    private final PauseTransition debounce;

    public BooksSection() {
        this.bookController = new BookController();
        this.searchField = new TextField();
        this.table = new TableView<>();
        this.categoryCombo = new ComboBox<>();
        this.orderCombo = new ComboBox<>();
        this.loadingSpinner = new ProgressIndicator();
        this.debounce = new PauseTransition(Duration.millis(300));

        styleContainer();
        initLoadingSpinner();

        getChildren().addAll(createTitle(), createTopBar(), loadingSpinner, createTable());

        refreshTable();
    }

    private void initLoadingSpinner() {
        loadingSpinner.setVisible(false);
        loadingSpinner.setPrefSize(50, 50);
        loadingSpinner.setStyle("-fx-progress-color: #34A853;");
        VBox.setMargin(loadingSpinner, new Insets(16, 0, 0, 0));
        setAlignment(Pos.TOP_CENTER);
    }

    private void styleContainer() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminBooks-style-1");
    }

    private HBox createTitle() {
        Label title = new Label("Manage Books");
        title.getStyleClass().add("adminBooks-style-2-1");

        HBox titleBar = new HBox(title);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(60);
        titleBar.setMaxWidth(Double.MAX_VALUE);
        titleBar.getStyleClass().add("adminBooks-style-2");
        return titleBar;
    }

    private HBox createTopBar() {
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

        Button searchButton = new Button("🔍 Search");
        searchButton.getStyleClass().add("adminBooks-style-3");
        searchButton.setOnAction(e -> refreshTable());


        Button addBookButton = new Button("+ Add Book");
        addBookButton.getStyleClass().add("adminBooks-style-4");
        addBookButton.setOnAction(e -> {
            new BookEditDialog().show(
                    (Stage) getScene().getWindow(),
                    new NullBook(),
                    this::refreshTable
            );
        });

        HBox topBar = new HBox(10, categoryCombo, orderCombo, new Region(), searchField, searchButton, addBookButton);
        HBox.setHgrow(topBar.getChildren().get(2), Priority.ALWAYS);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        return topBar;
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
                createColumn("Year", b -> String.valueOf(b.getDate().getYear())),
                createRatingColumn(),
                createActionsColumn()
        );

        return table;
    }

    private TableColumn<Book, String> createCoverColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Title");
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        column.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();
            private final HBox container = new HBox(10, imageView, label);

            {
                imageView.setFitWidth(32);
                imageView.setFitHeight(48);
                container.setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(String title, boolean empty) {
                super.updateItem(title, empty);
                if (empty || title == null || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Book book = getTableView().getItems().get(getIndex());
                    imageView.setImage(new Image(book.getUrlImage(), 32, 48, true, true));
                    label.setText(title);
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

    private TableColumn<Book, String> createRatingColumn() {
        TableColumn<Book, String> column = new TableColumn<>("Evaluation");
        column.setCellValueFactory(data -> new SimpleStringProperty("4.5"));
        column.setCellFactory(col -> new TableCell<>() {
            private final Label label = new Label("4.5 / 5");
            private final HBox container = new HBox(label);

            {
                container.setAlignment(Pos.CENTER);
                label.getStyleClass().add("adminBooks-style-5");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
        return column;
    }


    private TableColumn<Book, Void> createActionsColumn() {
        TableColumn<Book, Void> column = new TableColumn<>("Actions");
        column.setCellFactory(col -> new TableCell<>() {
            private final Button edit = new Button("✏");
            private final Button delete = new Button("🗑");
            private final HBox container = new HBox(8, edit, delete);

            {
                container.setAlignment(Pos.CENTER);
                edit.getStyleClass().add("adminBooks-style-6");
                delete.getStyleClass().add("adminBooks-style-7");

                edit.setOnAction(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    new BookEditDialog().show(
                            (Stage) getScene().getWindow(), book, BooksSection.this::refreshTable
                    );
                });

                delete.setOnAction(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    boolean removed = bookController.removeBook(book.getIsbn());
                    if (removed) table.getItems().remove(book);
                    else showError("Error deleting book.");
                });
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
        loadingSpinner.setVisible(true);
        bookController.loadBooksAsync(books -> {
            table.getItems().setAll(applyFilters(books));
            setControlsDisabled(false);
            loadingSpinner.setVisible(false);
        }, () -> {
            showError("Failed to load books.");
            setControlsDisabled(false);
            loadingSpinner.setVisible(false);
        });
    }

    private void setControlsDisabled(boolean disabled) {
        searchField.setDisable(disabled);
        categoryCombo.setDisable(disabled);
        orderCombo.setDisable(disabled);
        table.setDisable(disabled);
    }

    private List<Book> applyFilters(List<Book> books) {
        return filterBySearch(filterByCategoryAndOrder(books));
    }

    private List<Book> filterByCategoryAndOrder(List<Book> books) {
        BookCategoryType categoryType = BookCategoryType.fromLabel(categoryCombo.getValue());
        BookOrderType orderType = BookOrderType.fromLabel(orderCombo.getValue());
        return BookQueryUtils.getFilteredBooks(books, categoryType, orderType);
    }

    private List<Book> filterBySearch(List<Book> books) {
        String search = searchField.getText().toLowerCase();
        if (search.isBlank()) return books;
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(search)
                        || book.getAuthor().toLowerCase().contains(search))
                .toList();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}