package com.example.view.user;

import com.example.common.enums.BookCategoryType;
import com.example.common.interfaces.IBook;
import com.example.common.interfaces.ILoan;
import com.example.common.strategy.BookOrderType;
import com.example.controller.BookController;
import com.example.controller.LoanController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.manager.SessionManager;
import com.example.utils.BookQueryUtils;
import com.example.view.LoginView;
import com.example.view.user.components.BookCard;
import com.example.view.user.components.CategorySelector;
import com.example.view.user.components.LoanCard;
import com.example.view.user.components.TopBar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HomePage extends VBox {
    private final FlowPane bookGrid = new FlowPane();
    private final BookController bookController = BookController.getInstance();
    private final LoanController loanController = LoanController.getInstance();
    private final String[] selectedCategory = {"All"};
    private final ComboBox<String> orderCombo = new ComboBox<>();
    private final TextField searchBar = new TextField();

    public HomePage() {
        getStylesheets().add(getClass().getResource("/style/Homepage.css").toExternalForm());
        getStyleClass().add("root");

        createTopBar();
        createCategories();
        createLoanSection();
        createLoanListSection();
    }

    private void createTopBar() {
        TopBar topBar = new TopBar(searchBar,
                () -> new LoginView().start((Stage) getScene().getWindow()),
                this::refreshBooks
        );
        getChildren().add(topBar);
    }

    private void createCategories() {
        CategorySelector selector = new CategorySelector(
                selected -> {
                    selectedCategory[0] = selected;
                    refreshBooks();
                },
                this::refreshBooks
        );
        getChildren().add(selector);
    }

    private void createLoanSection() {
        VBox section = CreateSection(16);
        section.setStyle("-fx-padding: 32 32 0 32;");

        createBookGrid();
        refreshBooks(); // iniziale asincrono

        section.getChildren().addAll(CreateLabel("Added recently", "recent-label"), bookGrid);
        getChildren().add(section);
    }

    private VBox CreateSection(int spacing) {
        VBox section = new VBox();
        section.setSpacing(spacing);
        return section;
    }

    private Label CreateLabel(String title, String styleClass) {
        Label label = new Label(title);
        label.getStyleClass().add(styleClass);
        return label;
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
            bookGrid.getChildren().add(new BookCard(book));
        }
    }

    private void refreshBooks() {
        bookController.loadBooksAsync(books -> {
            BookOrderType orderType = BookOrderType.fromLabel(orderCombo.getValue());
            BookCategoryType categoryType = BookCategoryType.fromLabel(selectedCategory[0]);
            List<IBook> filtered = BookQueryUtils.getFilteredBooks(books, categoryType, orderType);

            if (!searchBar.getText().isBlank()) {
                String search = searchBar.getText().toLowerCase();
                filtered = filtered.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(search)
                                || book.getAuthor().toLowerCase().contains(search)
                                || book.getType().toLowerCase().contains(search))
                        .toList();
            }

            updateBooksUI(filtered);
        }, () -> {
            System.err.println("❌ Failed to load books.");
            updateBooksUI(new ArrayList<>()); // fallback vuoto
        });
    }

    private void createLoanListSection() {
        VBox section = CreateSection(24);
        section.setStyle("-fx-padding: 32 32 32 32;");

        loanController.loadLoansAsync(loans -> {
            section.getChildren().addAll(
                    createLoanSubsection("📚 In Progress", loans, ILoan::isInProgress, "#28a745"),
                    createLoanSubsection("⌛ Expired", loans, ILoan::isExpired, "#dc3545"),
                    createLoanSubsection("✔ Returned", loans, ILoan::isReturned, "#007bff")
            );
        }, () -> {
            System.err.println("❌ Failed to load loans.");
        });

        getChildren().add(section);
    }

    private VBox createLoanSubsection(String title, List<ILoan> loans, Predicate<ILoan> filter, String color) {
        VBox box = CreateSection(12);
        FlowPane loanGrid = CreateLoanGrid();

        List<ILoan> filteredLoans = loans.stream()
                .filter(loan -> loan.getUser().equals(SessionManager.getInstance().getLoggedUser()) && filter.test(loan))
                .toList();

        for (ILoan loan : filteredLoans) {
            loanGrid.getChildren().add(new LoanCard(loan, title.replaceAll("^[^a-zA-Z]+", ""), color));
        }

        box.getChildren().addAll(CreateLabel(title, "loan-subsection-title"), loanGrid);
        return box;
    }

    private FlowPane CreateLoanGrid() {
        FlowPane loanGrid = new FlowPane();
        loanGrid.setHgap(16);
        loanGrid.setVgap(16);
        loanGrid.setPrefWrapLength(800);
        loanGrid.setPadding(new Insets(4, 0, 0, 0));
        return loanGrid;
    }
}