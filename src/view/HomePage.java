package view;

import common.enums.BookCategoryType;
import common.strategy.BookOrderType;
import controller.LoanController;
import utils.BookQueryUtils;
import controller.BookController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import manager.SessionManager;
import common.interfaces.IBook;
import common.interfaces.IUser;
import common.interfaces.ILoan;
import java.util.function.Predicate;
import java.util.List;
import common.interfaces.ILoan;

public class HomePage extends VBox {
    private final FlowPane bookGrid = new FlowPane();
    private final BookController bookController = BookController.getInstance();
    private final LoanController loanController = LoanController.getInstance();
    private final String[] selectedCategory = {"All"};
    private final ComboBox<String> orderCombo = new ComboBox<>();
    private final TextField searchBar = new TextField();


    public HomePage() {
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: white;");
        setSpacing(16);

        createTopBar();
        createCategories();
        createLoanSection();
        createLoanListSection();
    }

    private void createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: white; -fx-padding: 16 24; -fx-border-color: #f4f4f4; -fx-border-width: 0 0 1 0;");
        topBar.setPrefHeight(50);
        topBar.setMaxHeight(Double.MAX_VALUE);
        topBar.setSpacing(8);

        Label icon = new Label("📖");
        icon.setStyle("-fx-font-size: 18px;");

        Label title = new Label("Digital-Library");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #222;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #ccc; -fx-border-radius: 20; -fx-padding: 4 8;");
        searchBox.setSpacing(6);

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 14px;");

        searchBar.setPromptText("Search Book");
        searchBar.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-font-size: 13px");

        // Ricerca dinamica
        searchBar.textProperty().addListener((obs, oldText, newText) -> refreshBooks());

        searchBox.getChildren().addAll(searchIcon, searchBar);

        Label userIcon = new Label("👤");
        userIcon.setStyle("-fx-font-size: 20px; -fx-cursor: hand;");

        ContextMenu userMenu = new ContextMenu();
        IUser user = SessionManager.getInstance().getLoggedUser();

        Label nameLabel = new Label(user.getName() + " " + user.getSurname());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #222;");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #777;");

        VBox textBox = new VBox(2, nameLabel, emailLabel);
        textBox.setStyle("""
             -fx-font-size: 13px; 
             -fx-background-color: transparent; 
             -fx-padding: 8 16 8 16;
        """);
        CustomMenuItem userInfoItem = new CustomMenuItem(textBox, false);

        MenuItem profile = new MenuItem("My Profile");
        profile.setStyle("-fx-font-size: 12px");

        MenuItem favorites = new MenuItem("Favorites");
        favorites.setStyle("-fx-font-size:12px");

        MenuItem logout = new MenuItem("↩ Logout");
        logout.setStyle("-fx-text-fill: red; -fx-font-size: 12px");
        logout.setOnAction(e -> {
            SessionManager.getInstance().logout();
            new LoginView().start((Stage) getScene().getWindow());
        });

        userMenu.getItems().addAll(userInfoItem, new SeparatorMenuItem(), profile, favorites, new SeparatorMenuItem(), logout);
        userIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                userMenu.show(userIcon, e.getScreenX(), e.getScreenY());
            }
        });

        topBar.getChildren().addAll(icon, title, spacer, searchBox, userIcon);

        getChildren().add(topBar);
    }

    private void createCategories() {
        VBox sectionWrapper = new VBox();
        sectionWrapper.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 16;
            -fx-border-radius: 16;
            -fx-padding: 24 32 24 32;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0.15, 0, 2);
        """);

        VBox container = new VBox();
        container.setSpacing(12);

        Label categoryTitle = new Label("Categories");
        categoryTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox categories = new HBox();
        categories.setSpacing(8);

        String[] categoryNames = {"All", "Narrative", "Non-fiction", "Science", "History", "Biography"};

        for (String name : categoryNames) {
            Button btn = new Button(name);
            btn.setStyle("""
            -fx-background-color: #f5f5f5;
            -fx-border-color: #e0e0e0;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
            -fx-padding: 6 12;
            -fx-font-size: 12px;
        """);
            btn.setOnMouseEntered(e -> btn.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-border-color: #bbb;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
            -fx-padding: 6 12;
            -fx-font-size: 12px;
        """));
            btn.setOnMouseExited(e -> btn.setStyle("""
            -fx-background-color: #f5f5f5;
            -fx-border-color: #e0e0e0;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
            -fx-padding: 6 12;
            -fx-font-size: 12px;
        """));
            btn.setOnMousePressed(e -> btn.setStyle("""
            -fx-background-color: #d0d0d0;
            -fx-border-color: #aaa;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
            -fx-padding: 6 12;
            -fx-font-size: 12px;
        """));
            btn.setOnMouseReleased(e -> btn.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-border-color: #bbb;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
            -fx-padding: 6 12;
            -fx-font-size: 12px;
        """));

            btn.setOnAction(e -> {
                selectedCategory[0] = name;
                refreshBooks();
            });
            categories.getChildren().add(btn);
        }

        HBox orderBox = new HBox();
        orderBox.setSpacing(8);
        orderBox.setAlignment(Pos.CENTER_RIGHT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label orderLabel = new Label("Order by:");
        orderLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333;");

        orderCombo.getItems().addAll(
                BookOrderType.MOST_RECENT.getLabel(),
                BookOrderType.AUTHOR.getLabel(),
                BookOrderType.TITLE_A_Z.getLabel()
        );
        orderCombo.setValue(BookOrderType.MOST_RECENT.getLabel());

        orderCombo.setStyle("""
        -fx-background-color: white;
        -fx-border-color: #ccc;
        -fx-border-radius: 8;
        -fx-background-radius: 8;
        -fx-padding: 4 8;
        -fx-font-size: 12px;
        -fx-cursor: hand;
    """);

        orderCombo.setOnMouseEntered(e -> orderCombo.setStyle("""
        -fx-background-color: #f0f0f0;
        -fx-border-color: #aaa;
        -fx-border-radius: 8;
        -fx-background-radius: 8;
        -fx-padding: 4 8;
        -fx-font-size: 12px;
        -fx-cursor: hand;
    """));

        orderCombo.setOnMouseExited(e -> orderCombo.setStyle("""
        -fx-background-color: white;
        -fx-border-color: #ccc;
        -fx-border-radius: 8;
        -fx-background-radius: 8;
        -fx-padding: 4 8;
        -fx-font-size: 12px;
        -fx-cursor: hand;
    """));

        orderCombo.setOnAction(e -> {
            refreshBooks();
            orderCombo.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-border-color: #aaa;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-padding: 4 8;
            -fx-font-size: 12px;
            -fx-cursor: hand;
        """);
        });

        orderBox.getChildren().addAll(spacer, orderLabel, orderCombo);

        HBox topRow = new HBox(categories, orderBox);
        topRow.setSpacing(12);
        topRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(orderBox, Priority.ALWAYS);

        container.getChildren().addAll(categoryTitle, topRow);
        sectionWrapper.getChildren().add(container);
        getChildren().add(sectionWrapper);
    }


    private void createLoanSection() {
        VBox section = new VBox();
        section.setSpacing(16);
        section.setStyle("-fx-padding: 32 32 0 32;");

        Label recentLabel = new Label("Added recently");
        recentLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        createBookGrid(); // setup layout
        refreshBooks(); // initial load

        section.getChildren().addAll(recentLabel, bookGrid);

        getChildren().add(section);
    }

    private void createBookGrid() {
        bookGrid.setPadding(new Insets(16, 0, 0, 0));
        bookGrid.setHgap(16);
        bookGrid.setVgap(16);
        bookGrid.setPrefWrapLength(800);
        bookGrid.setAlignment(Pos.TOP_LEFT);
    }

    private void updateBooksUI(List<IBook> books) {
        bookGrid.getChildren().clear();

        for (IBook book : books) {
            VBox bookCard = new VBox();
            bookCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.1, 0, 1);");
            bookCard.setSpacing(6);
            bookCard.setPrefWidth(140);
            bookCard.setAlignment(Pos.TOP_LEFT);

            bookCard.setOnMouseClicked(e -> {
                new BookDetailView().show((Stage) getScene().getWindow(), book);
            });
            bookCard.setOnMouseEntered(ev -> bookCard.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 12; -fx-padding: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.1, 0, 1); -fx-cursor: hand"));
            bookCard.setOnMouseExited(ev -> bookCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.1, 0, 1);"));



            ImageView image = new ImageView(new Image(book.getUrlImage()));
            image.setFitWidth(140);
            image.setFitHeight(180);

            Label title = new Label(book.getTitle());
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

            Label author = new Label(book.getAuthor());
            author.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

            Label availability = new Label(book.available() ? "✓ Available" : "✗ Not Available");
            availability.setStyle("-fx-font-size: 12px; -fx-text-fill: " + (book.available() ? "#28a745" : "#dc3545") + ";");

            bookCard.getChildren().addAll(image, title, author, availability);
            bookGrid.getChildren().add(bookCard);
        }
    }

    private void refreshBooks() {
        List<IBook> books = bookController.getAllBooks();
        BookOrderType orderType = BookOrderType.fromLabel(orderCombo.getValue());

        BookCategoryType categoryType = BookCategoryType.fromLabel(selectedCategory[0]);
        List<IBook> filtered = BookQueryUtils.getFilteredBooks(books, categoryType, orderType);


        if (!searchBar.getText().isBlank()) {
            String search = searchBar.getText().toLowerCase();
            filtered = filtered.stream()
                    .filter(book ->
                            book.getTitle().toLowerCase().contains(search) ||
                            book.getAuthor().toLowerCase().contains(search) ||
                            book.getType().toLowerCase().contains(search)
                    )
                    .toList();
        }

        updateBooksUI(filtered);
    }


    private void createLoanListSection() {
        VBox section = new VBox();
        section.setSpacing(24);
        section.setStyle("-fx-padding: 32 32 32 32;");

        section.getChildren().addAll(
                createLoanSubsection("📚 In Progress", ILoan::isInProgress, "#28a745"),
                createLoanSubsection("⌛ Expired", ILoan::isExpired, "#dc3545"),
                createLoanSubsection("✔ Returned", ILoan::isReturned, "#007bff")
        );

        getChildren().add(section);
    }

    private VBox createLoanSubsection(String title, Predicate<ILoan> filter, String color) {
        VBox box = new VBox();
        box.setSpacing(12);

        Label label = new Label(title);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        FlowPane loanGrid = new FlowPane();
        loanGrid.setHgap(16);
        loanGrid.setVgap(16);
        loanGrid.setPrefWrapLength(800);
        loanGrid.setPadding(new Insets(4, 0, 0, 0));

        List<ILoan> filteredLoans = loanController.searchLoans(loan ->
                loan.getUser().equals(SessionManager.getInstance().getLoggedUser()) && filter.test(loan)
        );

        for (ILoan loan : filteredLoans) {
            VBox card = new VBox();
            card.setSpacing(6);
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0.1, 0, 1);");
            card.setPrefWidth(180);
            card.setOnMouseClicked(e -> new BookDetailView().show((Stage) getScene().getWindow(), loan.getBook()));

            Label bookTitle = new Label(loan.getBook().getTitle());
            bookTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

            Label dueDate = new Label(
                    loan.isReturned() ?
                            "Returned on: " + loan.getReturnDate() :
                            "Due: " + loan.getExpirationDate()
            );
            dueDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");

            String statusText = "Status: " + title.replaceAll("^[^a-zA-Z]+", "");
            Label status = new Label(statusText);
            status.setStyle("-fx-font-size: 12px; -fx-text-fill: " + color + ";");

            if (loan.isExpired()) {
                status.setText(status.getText() + " ⚠");
                Tooltip tip = new Tooltip("This loan has expired. Please return the book.");
                Tooltip.install(card, tip);
                card.setStyle(card.getStyle() + "-fx-border-color: red; -fx-border-width: 1;");
            }

            card.getChildren().addAll(bookTitle, dueDate, status);
            loanGrid.getChildren().add(card);
        }

        box.getChildren().addAll(label, loanGrid);
        return box;
    }

}
