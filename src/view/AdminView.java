package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import manager.BookManager;
import manager.LoanManager;
import manager.SessionManager;
import manager.UserManager;
import model.User;

public class AdminView {
    private final User user;

    public AdminView() {
        SessionManager session = SessionManager.getInstance();
        this.user = session.getLoggedUser();
    }

    public void start(Stage stage) {
        VBox sidebar = createSidebar(stage);
        VBox dashboard = createDashboard();

        HBox root = new HBox(sidebar, dashboard);
        HBox.setHgrow(dashboard, Priority.ALWAYS);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Digital Library - Admin Dashboard");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createSidebar(Stage stage) {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #0D1B2A; -fx-padding: 10 20 0 20; -fx-spacing: 20;");
        sidebar.setPrefWidth(220);
        sidebar.setMinWidth(220);
        sidebar.setMaxWidth(220);

        Label title = new Label("📚 Digital Library");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button dashboardBtn = new Button("📊 Dashboard");
        Button booksBtn = new Button("📚 Books");
        Button usersBtn = new Button("👥 Users");
        Button settingsBtn = new Button("⚙ Settings");

        for (Button btn : new Button[]{dashboardBtn, booksBtn, usersBtn, settingsBtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        Label initials = new Label(user.getName() + " " + user.getSurname());
        initials.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        VBox userInfo = new VBox(initials, emailLabel);
        userInfo.setSpacing(4);

        Button logoutButton = new Button("⎋");
        logoutButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff4d4d; -fx-font-size: 24px; -fx-cursor: hand;");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            new LoginView().start(stage);
        });

        HBox userBar = new HBox(userInfo, logoutButton);
        userBar.setSpacing(5);
        userBar.setAlignment(Pos.CENTER);

        sidebar.getChildren().addAll(title, dashboardBtn, booksBtn, usersBtn, settingsBtn, spacer, userBar);
        return sidebar;
    }

    private VBox createDashboard() {
        VBox dashboard = new VBox();
        dashboard.setMaxWidth(Double.MAX_VALUE);
        dashboard.setStyle("-fx-background-color: #e5e5e5;");

        HBox topbar = new HBox();
        topbar.setStyle("-fx-background-color: white; -fx-padding: 16 24; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setPrefHeight(60);
        topbar.setMaxWidth(Double.MAX_VALUE);

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #222;");
        topbar.getChildren().add(dashboardTitle);

        Label panoramicTitle = new Label("Panoramic");
        panoramicTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 24 0 8 24;");

        HBox statsBox = new HBox(15);
        statsBox.setMaxWidth(Double.MAX_VALUE);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(0, 24, 24, 24));
        statsBox.getChildren().addAll(
                statCard("📚", "Totale Libri", String.valueOf(BookManager.getInstance().getAll().size())),
                statCard("👥", "Utenti Registrati", String.valueOf(UserManager.getInstance().getAll().size())),
                statCard("🔄", "Prestiti Totali", String.valueOf(LoanManager.getInstance().getAll().size()))
        );

        dashboard.getChildren().addAll(topbar, panoramicTitle, statsBox);
        return dashboard;
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
