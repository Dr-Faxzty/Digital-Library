package view;

import controller.BookFilterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import manager.BookManager;
import manager.SessionManager;
import model.Book;
import model.User;

import java.util.List;

public class HomePage extends VBox {
    private final FlowPane bookGrid = new FlowPane();
    private final BookFilterController filterController = new BookFilterController();
    private final String[] selectedCategory = {"All"};
    private final ComboBox<String> orderCombo = new ComboBox<>();
    private final TextField searchBar = new TextField();

    public HomePage() {
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: #e5e5e5;");
        setSpacing(16);

        createTopBar();
        createCategories();
        createLoanSection();
    }

    private void createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #f6f3f3; -fx-padding: 16 24; -fx-border-color: #fdfafa; -fx-border-width: 0 0 1 0;");
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
        User user = SessionManager.getInstance().getLoggedUser();

        Label nameLabel = new Label(user.getName() + " " + user.getSurname());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #222;");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #777;");

        VBox textBox = new VBox(2, nameLabel, emailLabel);
        textBox.setStyle("-fx-font-size: 13px; -fx-background-color: transparent; -fx-padding: 8 16 8 16;");
        CustomMenuItem userInfoItem = new CustomMenuItem(textBox, false);

        MenuItem profile = new MenuItem("My Profile");
        MenuItem favorites = new MenuItem("Favorites");
        MenuItem logout = new MenuItem("↩ Logout");
        logout.setStyle("-fx-text-fill: red;");
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
        VBox container = new VBox();
        container.setSpacing(12);
        container.setStyle("-fx-padding: 24 32 0 32;");

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
        orderLabel.setStyle("-fx-font-size: 12px; -fx-text-fill_ #333;");

        orderCombo.getItems().addAll("Most recent", "Author", "Titles A-Z");
        orderCombo.setValue("Most recent");

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
        getChildren().add(container);
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

    private void updateBooksUI(List<Book> books) {
        bookGrid.getChildren().clear();

        for (Book book : books) {
            VBox bookCard = new VBox();
            bookCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.1, 0, 1);");
            bookCard.setSpacing(6);
            bookCard.setPrefWidth(140);
            bookCard.setAlignment(Pos.TOP_LEFT);

            ImageView image = new ImageView(new Image(book.getUrlImage()));
            image.setFitWidth(140);
            image.setFitHeight(180);

            Label title = new Label(book.getTitle());
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

            Label author = new Label(book.getAuthor());
            author.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

            Label availability = new Label(book.isAvailable() ? "✓ Available" : "✗ Not Available");
            availability.setStyle("-fx-font-size: 12px; -fx-text-fill: " + (book.isAvailable() ? "#28a745" : "#dc3545") + ";");

            bookCard.getChildren().addAll(image, title, author, availability);
            bookGrid.getChildren().add(bookCard);
        }
    }

    private void refreshBooks() {
        List<Book> filtered = filterController.getFilteredBooks(selectedCategory[0], orderCombo.getValue());

        if (!searchBar.getText().isBlank()) {
            String search = searchBar.getText().toLowerCase();
            filtered = filtered.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(search))
                    .toList();
        }

        updateBooksUI(filtered);
    }
}
