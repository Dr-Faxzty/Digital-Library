package view.admin;

import common.interfaces.IBook;
import common.interfaces.ILoan;
import common.interfaces.IUser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import controller.BookController;
import controller.UserController;
import controller.LoanController;
import common.observer.ViewObserver;
import common.observer.ViewSubject;
import view.admin.components.RecentBooksBox;
import view.admin.components.RecentUsersBox;

import java.util.List;

public class Dashboard extends VBox implements ViewSubject {
    private final BookController bookController;
    private final UserController userController;
    private final LoanController loanController;
    private List<IBook> books;
    private List<IUser> users;
    private List<ILoan> loans;

    private ViewObserver observer;

    public Dashboard() {
        this.bookController = BookController.getInstance();
        this.userController = UserController.getInstance();
        this.loanController = LoanController.getInstance();

        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminDashboard-style-1");

        createTopBar();
        createPanoramic();
        loadDashboardData();
    }

    @Override
    public void setObserver(ViewObserver observer) {
        this.observer = observer;
    }

    private void createTopBar() {
        HBox topbar = new HBox();
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setPrefHeight(60);
        topbar.setMaxWidth(Double.MAX_VALUE);
        topbar.getStyleClass().add("adminDashboard-style-2");

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.getStyleClass().add("adminDashboard-style-3");
        topbar.getChildren().add(dashboardTitle);

        getChildren().add(topbar);
    }

    private void createPanoramic() {
        Label panoramicTitle = new Label("Panoramic");
        panoramicTitle.getStyleClass().add("adminDashboard-style-4");
        getChildren().add(panoramicTitle);
    }

    private void createStats() {
        HBox statsBox = new HBox(15);
        statsBox.setMaxWidth(Double.MAX_VALUE);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(0, 24, 24, 24));

        statsBox.getChildren().addAll(
                statCard("📚", "Total Books", String.valueOf(bookController.getAllBooks().size()), "books"),
                statCard("👥", "Registered Users", String.valueOf(userController.getAllUsers().size()), "users"),
                statCard("🔄", "Total Loans", String.valueOf(loanController.getAllLoans().size()), "loans")
        );

        getChildren().add(statsBox);
    }

    private void createRecentSections() {
        HBox recentSection = new HBox(24);
        recentSection.setPadding(new Insets(20, 24, 24, 24));
        recentSection.setAlignment(Pos.TOP_CENTER);

        VBox recentBooks = RecentBooksBox.create(bookController, observer);
        VBox recentUsers = RecentUsersBox.create(userController, observer);

        HBox.setHgrow(recentBooks, Priority.ALWAYS);
        HBox.setHgrow(recentUsers, Priority.ALWAYS);

        recentSection.getChildren().addAll(recentBooks, recentUsers);
        getChildren().add(recentSection);
    }

    private VBox statCard(String icon, String title, String value, String viewName) {
        VBox content = new VBox(4);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(10, 12, 10, 12));

        HBox top = new HBox(8);
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("adminDashboard-style-5");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("adminDashboard-style-6");
        top.getChildren().addAll(iconLabel, titleLabel);
        top.setAlignment(Pos.CENTER_LEFT);

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("adminDashboard-style-7");

        content.getChildren().addAll(top, valueLabel);

        Label actionLabel = new Label("View All");
        actionLabel.getStyleClass().add("adminDashboard-style-8");
        actionLabel.setOnMouseEntered(e -> actionLabel.getStyleClass().add("adminDashboard-style-9"));
        actionLabel.setOnMouseExited(e -> actionLabel.getStyleClass().add("adminDashboard-style-8"));
        actionLabel.setOnMouseClicked(e -> notifyObserver(viewName));


        VBox card = new VBox(content, actionLabel);
        card.getStyleClass().add("adminDashboard-style-10");
        card.setPrefSize(200, 100);
        return card;
    }

    private void notifyObserver(String viewName) {
        if (observer != null) {
            observer.onViewChange(viewName);
        }
    }

    private void renderDashboard() {
        createStats();
        if (observer != null) createRecentSections();
    }

    private void loadDashboardData() {
        bookController.loadBooksAsync(
                books -> {
                    this.books = books;
                    if (this.users != null && this.loans != null) renderDashboard();
                },
                () -> showError("Failed to load books.")
        );

        userController.loadUsersAsync(
                users -> {
                    this.users = users;
                    if (this.books != null && this.loans != null) renderDashboard();
                },
                () -> showError("Failed to load users.")
        );

        loanController.loadLoansAsync(
                loans -> {
                    this.loans = loans;
                    if (this.books != null && this.users != null) renderDashboard();
                },
                () -> showError("Failed to load loans.")
        );
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
