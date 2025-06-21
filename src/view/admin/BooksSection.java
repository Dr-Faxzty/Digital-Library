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
import common.interfaces.IBook;
import javafx.util.Duration;
import controller.BookController;
import common.nullObject.NullBook;
import utils.BookQueryUtils;
import view.admin.components.BookEditDialog;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BooksSection extends VBox {
    private final BookController bookController = BookController.getInstance();
    private final TextField searchField = new TextField();
    private final TableView<IBook> table = new TableView<>();
    private final ComboBox<String> categoryCombo = new ComboBox<>();
    private final ComboBox<String> orderCombo = new ComboBox<>();
    private final ProgressIndicator loadingSpinner = new ProgressIndicator();
    private final PauseTransition debounce = new PauseTransition(Duration.millis(300));

    public BooksSection() {
        setupLayout();
        refreshTable();
    }

    private void setupLayout() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminBooks-style-1");
        setAlignment(Pos.TOP_CENTER);
        initLoadingSpinner();
        getChildren().addAll(createTitle(), createTopBar(), loadingSpinner, createTable());
    }

    private void initLoadingSpinner() {
        loadingSpinner.setVisible(false);
        loadingSpinner.setManaged(false);
        loadingSpinner.setPrefSize(50, 50);
        loadingSpinner.setStyle("-fx-progress-color: #34A853;");
        VBox.setMargin(loadingSpinner, new Insets(16, 0, 0, 0));
    }

    private void showLoadingSpinner(boolean show) {
        loadingSpinner.setVisible(show);
        loadingSpinner.setManaged(show);
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
        setupComboBox(categoryCombo, BookCategoryType.values(), BookCategoryType::getLabel, "All", e -> refreshTable());
        setupComboBox(orderCombo, BookOrderType.values(), BookOrderType::getLabel, BookOrderType.TITLE_A_Z.getLabel(), e -> refreshTable());

        searchField.setPromptText("Search books...");
        searchField.setPrefWidth(200);
        searchField.getStyleClass().add("adminBooks-style-2-2");

        Button searchButton = createStyledButton("\uD83D\uDD0D Search", "adminBooks-style-3", e -> refreshTable());
        Button addBookButton = createStyledButton("+ Add Book", "adminBooks-style-4", e -> openAddDialog());

        HBox topBar = new HBox(10, categoryCombo, orderCombo, new Region(), searchField, searchButton, addBookButton);
        HBox.setHgrow(topBar.getChildren().get(2), Priority.ALWAYS);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(16, 24, 0, 24));

        return topBar;
    }

    private <T> void setupComboBox(ComboBox<String> combo, T[] values, Function<T, String> labelMapper, String defaultValue, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        combo.getItems().addAll(Arrays.stream(values).map(labelMapper).toList());
        combo.setValue(defaultValue);
        combo.setOnAction(handler);
        combo.getStyleClass().add("adminBooks-category-combo");
    }

    private Button createStyledButton(String text, String styleClass, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);
        button.setOnAction(action);
        return button;
    }

    private void openAddDialog() {
        new BookEditDialog().show((Stage) getScene().getWindow(), new NullBook(), this::refreshTable);
    }

    private TableView<IBook> createTable() {
        table.getStyleClass().add("adminBooks-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
        table.setPlaceholder(new Label(""));
        VBox.setMargin(table, new Insets(16, 24, 24, 24));

        table.getColumns().addAll(
                createCoverColumn(),
                createColumn("Author", IBook::getAuthor),
                createColumn("Category", IBook::getType),
                createColumn("Year", book -> String.valueOf(book.getDate().getYear())),
                createRatingColumn(),
                createActionsColumn()
        );

        return table;
    }

    private TableColumn<IBook, String> createCoverColumn() {
        TableColumn<IBook, String> column = new TableColumn<>("Title");
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
                    IBook book = getTableView().getItems().get(getIndex());
                    imageView.setImage(new Image(book.getUrlImage(), 32, 48, true, true));
                    label.setText(title);
                    setGraphic(container);
                }
            }
        });
        return column;
    }

    private TableColumn<IBook, String> createColumn(String name, Function<IBook, String> mapper) {
        TableColumn<IBook, String> column = new TableColumn<>(name);
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

    private TableColumn<IBook, String> createRatingColumn() {
        TableColumn<IBook, String> column = new TableColumn<>("Evaluation");
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

    private TableColumn<IBook, Void> createActionsColumn() {
        TableColumn<IBook, Void> column = new TableColumn<>("Actions");
        column.setCellFactory(col -> new TableCell<>() {
            private final Button edit = createStyledButton("\u270F", "adminBooks-style-6", e -> openEditDialog());
            private final Button delete = createStyledButton("\uD83D\uDDD1", "adminBooks-style-7", e -> deleteBook());
            private final HBox container = new HBox(8, edit, delete);

            {
                container.setAlignment(Pos.CENTER);
            }

            private void openEditDialog() {
                IBook book = getTableView().getItems().get(getIndex());
                new BookEditDialog().show((Stage) getScene().getWindow(), book, BooksSection.this::refreshTable);
            }

            private void deleteBook() {
                IBook book = getTableView().getItems().get(getIndex());
                boolean removed = bookController.removeBook(book.getIsbn());
                if (removed) table.getItems().remove(book);
                else showError("Error deleting book.");
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
        bookController.loadBooksAsync(books -> {
            table.getItems().setAll(applyFilters(books));
            setControlsDisabled(false);
            showLoadingSpinner(false);
        }, () -> {
            showError("Failed to load books.");
            setControlsDisabled(false);
            showLoadingSpinner(false);
        });
    }

    private void setControlsDisabled(boolean disabled) {
        searchField.setDisable(disabled);
        categoryCombo.setDisable(disabled);
        orderCombo.setDisable(disabled);
        table.setDisable(disabled);
    }

    private List<IBook> applyFilters(List<IBook> books) {
        return filterBySearch(filterByCategoryAndOrder(books));
    }

    private List<IBook> filterByCategoryAndOrder(List<IBook> books) {
        BookCategoryType categoryType = BookCategoryType.fromLabel(categoryCombo.getValue());
        BookOrderType orderType = BookOrderType.fromLabel(orderCombo.getValue());
        return BookQueryUtils.getFilteredBooks(books, categoryType, orderType);
    }

    private List<IBook> filterBySearch(List<IBook> books) {
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
