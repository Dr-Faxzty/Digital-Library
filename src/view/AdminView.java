package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import manager.SessionManager;
import model.User;

public class AdminView {
    private final SessionManager session;
    private final User user;

    public AdminView() {
        this.session = SessionManager.getInstance();
        this.user = session.getLoggedUser();
    }

    public void start(Stage stage) {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #0D1B2A; -fx-padding: 20; -fx-spacing: 20;");
        sidebar.setPrefWidth(220);

        Label title = new Label("📚 Digital Library");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button dashboardBtn = new Button("\uD83D\uDCCA Dashboard");
        Button booksBtn = new Button("\uD83D\uDCDA Books");
        Button usersBtn = new Button("\uD83D\uDC65 Users");
        Button settingsBtn = new Button("⚙ Settings");

        for (Button btn : new Button[]{dashboardBtn, booksBtn, usersBtn, settingsBtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        Label initials = new Label(user.getName() + " " + user.getSurname());
        initials.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        VBox userInfo = new VBox(initials, emailLabel);
        userInfo.setSpacing(4);

        Button logoutButton = new Button("\uF2F5");
        logoutButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff4d4d; -fx-font-size: 12px; -fx-cursor: hand;");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            new LoginView().start(stage);
        });

        HBox userBar = new HBox(userInfo, logoutButton);
        userBar.setSpacing(10);
        userBar.setAlignment(Pos.CENTER_LEFT);


        sidebar.getChildren().addAll(title, dashboardBtn, booksBtn, usersBtn, settingsBtn, spacer, userBar);

        VBox dashboard = new VBox(15);
        dashboard.setPadding(new Insets(20));

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.setFont(new Font("Arial", 20));

        HBox statsBox = new HBox(15);
        statsBox.setPadding(new Insets(10));

        statsBox.getChildren().addAll(
                statCard("\uD83D\uDCDA Total Books", "10"),
                statCard("\uD83D\uDC65 Registered Users", "3"),
                statCard("\uD83D\uDC41 Views Today", "-"));

        dashboard.getChildren().addAll(dashboardTitle, statsBox);

        HBox root = new HBox();
        root.getChildren().addAll(sidebar, dashboard);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Digital Library - Admin Dashboard");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    private VBox statCard(String title, String value) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #f4f9f6; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180, 80);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #333333;");

        card.getChildren().addAll(valueLabel, titleLabel);
        return card;
    }
}