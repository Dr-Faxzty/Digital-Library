package view.user;

import common.enums.BookCategoryType;
import common.strategy.BookOrderType;
import controller.LoanController;
import utils.BookQueryUtils;
import controller.BookController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import manager.SessionManager;
import common.interfaces.IBook;
import common.interfaces.ILoan;
import view.LoginView;
import view.user.components.BookCard;
import view.user.components.CategorySelector;
import view.user.components.LoanCard;
import view.user.components.TopBar;

import java.util.function.Predicate;
import java.util.List;

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
        refreshBooks();

        section.getChildren().addAll(CreateLabel("Added recently", "recent-label"), bookGrid);
        getChildren().add(section);
    }

    private VBox CreateSection(int x){
        VBox section = new VBox();
        section.setSpacing(x);

        return section;
    }

    private Label CreateLabel(String title, String StyleClass) {
        Label recentLabel = new Label(title);
        recentLabel.getStyleClass().add(StyleClass);

        return recentLabel;
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
        VBox section = CreateSection(24);
        setStyle("-fx-padding: 32 32 32 32;");

        section.getChildren().addAll(
                createLoanSubsection("\uD83D\uDCDA In Progress", ILoan::isInProgress, "#28a745"),
                createLoanSubsection("⌛ Expired", ILoan::isExpired, "#dc3545"),
                createLoanSubsection("✔ Returned", ILoan::isReturned, "#007bff")
        );

        getChildren().add(section);
    }

    private VBox createLoanSubsection(String title, Predicate<ILoan> filter, String color) {
        VBox box = CreateSection(12);

        FlowPane loanGrid = CreateLoanGrid();

        List<ILoan> filteredLoans = loanController.searchLoans(loan ->
                loan.getUser().equals(SessionManager.getInstance().getLoggedUser()) && filter.test(loan)
        );

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
