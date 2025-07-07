package com.example.view.admin;

import com.example.common.interfaces.IBook;
import com.example.common.interfaces.ILoan;
import com.example.common.interfaces.IUser;
import com.example.common.observer.ViewObserver;
import com.example.common.observer.ViewSubject;
import com.example.controller.BookController;
import com.example.controller.LoanController;
import com.example.controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import com.example.view.admin.components.RecentBooksBox;
import com.example.view.admin.components.RecentUsersBox;

import java.util.List;

public class Dashboard extends VBox implements ViewSubject {
    private final BookController bookController = BookController.getInstance();
    private final UserController userController = UserController.getInstance();
    private final LoanController loanController = LoanController.getInstance();
    private List<IBook> books;
    private List<IUser> users;
    private List<ILoan> loans;
    private ViewObserver observer;

    public Dashboard() {
        setupLayout();
        loadDashboardData();
    }

    private void setupLayout() {
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("adminDashboard-style-1");
        getChildren().addAll(createTopBar(), createPanoramicLabel());
    }

    @Override
    public void setObserver(ViewObserver observer) {
        this.observer = observer;
    }

    private HBox createTopBar() {
        HBox topbar = new HBox();
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setPrefHeight(60);
        topbar.setMaxWidth(Double.MAX_VALUE);
        topbar.getStyleClass().add("adminDashboard-style-2");

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.getStyleClass().add("adminDashboard-style-3");
        topbar.getChildren().add(dashboardTitle);
        return topbar;
    }

    private Label createPanoramicLabel() {
        Label panoramicTitle = new Label("Panoramic");
        panoramicTitle.getStyleClass().add("adminDashboard-style-4");
        return panoramicTitle;
    }

    private void createStats() {
        HBox statsBox = new HBox(15);
        statsBox.setMaxWidth(Double.MAX_VALUE);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(0, 24, 24, 24));

        statsBox.getChildren().addAll(
                createStatCard("\uD83D\uDCDA", "Total Books", String.valueOf(books.size()), "books"),
                createStatCard("\uD83D\uDC65", "Registered Users", String.valueOf(users.size()), "users"),
                createStatCard("\uD83D\uDD04", "Total Loans", String.valueOf(loans.size()), "loans")
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

    private VBox createStatCard(String icon, String title, String value, String viewName) {
        VBox content = new VBox(4);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(10, 12, 10, 12));

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("adminDashboard-style-5");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("adminDashboard-style-6");
        top.getChildren().addAll(iconLabel, titleLabel);

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("adminDashboard-style-7");

        Label actionLabel = createActionLabel(viewName);

        VBox card = new VBox(content, actionLabel);
        content.getChildren().addAll(top, valueLabel);
        card.getStyleClass().add("adminDashboard-style-10");
        card.setPrefSize(200, 100);
        return card;
    }

    private Label createActionLabel(String viewName) {
        Label actionLabel = new Label("View All");
        actionLabel.getStyleClass().add("adminDashboard-style-8");
        actionLabel.setOnMouseEntered(e -> actionLabel.getStyleClass().add("adminDashboard-style-9"));
        actionLabel.setOnMouseExited(e -> actionLabel.getStyleClass().add("adminDashboard-style-8"));
        actionLabel.setOnMouseClicked(e -> notifyObserver(viewName));
        return actionLabel;
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
                    checkAndRender();
                },
                () -> showError("Failed to load books.")
        );

        userController.loadUsersAsync(
                users -> {
                    this.users = users;
                    checkAndRender();
                },
                () -> showError("Failed to load users.")
        );

        loanController.loadLoansAsync(
                loans -> {
                    this.loans = loans;
                    checkAndRender();
                },
                () -> showError("Failed to load loans.")
        );
    }

    private void checkAndRender() {
        if (books != null && users != null && loans != null) {
            renderDashboard();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}