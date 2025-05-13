package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import manager.BookManager;
import manager.LoanManager;
import manager.UserManager;
import view.components.RecentBooksBox;

public class AdminDashboard extends VBox {

    public AdminDashboard() {
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: #e5e5e5;");

        createTopBar();
        createPanoramic();
        createStats();
        createRecentBooks();
    }

    private void createTopBar() {
        HBox topbar = new HBox();
        topbar.setStyle("-fx-background-color: white; -fx-padding: 16 24; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setPrefHeight(60);
        topbar.setMaxWidth(Double.MAX_VALUE);

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #222;");
        topbar.getChildren().add(dashboardTitle);

        getChildren().add(topbar);
    }

    private void createPanoramic() {
        Label panoramicTitle = new Label("Panoramic");
        panoramicTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 24 0 8 24;");
        getChildren().add(panoramicTitle);
    }

    private void createStats() {
        HBox statsBox = new HBox(15);
        statsBox.setMaxWidth(Double.MAX_VALUE);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(0, 24, 24, 24));

        statsBox.getChildren().addAll(
                statCard("📚", "Total Books", String.valueOf(BookManager.getInstance().getAll().size())),
                statCard("👥", "Registered Users", String.valueOf(UserManager.getInstance().getAll().size())),
                statCard("🔄", "Total Loans", String.valueOf(LoanManager.getInstance().getAll().size()))
        );

        getChildren().add(statsBox);
    }

    private void createRecentBooks() {
        VBox recentBooks = RecentBooksBox.create();
        VBox.setMargin(recentBooks, new Insets(20, 24, 24, 24));
        getChildren().add(recentBooks);
    }

    private VBox statCard(String icon, String title, String value) {
        VBox content = new VBox(4);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(10, 12, 10, 12));

        HBox top = new HBox(8);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 18px;");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");
        top.getChildren().addAll(iconLabel, titleLabel);
        top.setAlignment(Pos.CENTER_LEFT);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #000;");

        content.getChildren().addAll(top, valueLabel);

        Label actionLabel = new Label("View All");
        actionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #007BFF; -fx-padding: 8 0 8 12;");
        actionLabel.setOnMouseEntered(e -> actionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #0056b3; -fx-padding: 8 0 8 12; -fx-cursor: hand;"));
        actionLabel.setOnMouseExited(e -> actionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #007BFF; -fx-padding: 8 0 8 12;"));

        VBox card = new VBox(content, actionLabel);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 0; -fx-border-color: #ccc; -fx-border-radius: 10;");
        card.setPrefSize(200, 100);
        return card;
    }
}
