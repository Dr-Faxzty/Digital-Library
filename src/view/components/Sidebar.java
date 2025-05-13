package view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;
import manager.SessionManager;
import view.LoginView;

public class Sidebar extends VBox {

    public Sidebar(User user) {
        setStyle("-fx-background-color: #0D1B2A; -fx-padding: 10 20 0 20; -fx-spacing: 20;");
        setPrefWidth(220);
        setMinWidth(220);
        setMaxWidth(220);

        getChildren().addAll(
                createTitle(),
                createNavButtons(),
                createSpacer(),
                createUserBar(user)
        );
    }

    private Label createTitle() {
        Label title = new Label("📚 Digital Library");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        return title;
    }

    private VBox createNavButtons() {
        VBox buttonsBox = new VBox(20);
        Button dashboardBtn = new Button("📊 Dashboard");
        Button booksBtn = new Button("📚 Books");
        Button usersBtn = new Button("👥 Users");
        Button settingsBtn = new Button("⚙ Settings");

        for (Button btn : new Button[]{dashboardBtn, booksBtn, usersBtn, settingsBtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        }

        buttonsBox.getChildren().addAll(dashboardBtn, booksBtn, usersBtn, settingsBtn);
        return buttonsBox;
    }

    private Region createSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private HBox createUserBar(User user) {
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
            new LoginView().start((Stage) getScene().getWindow());
        });

        HBox userBar = new HBox(userInfo, logoutButton);
        userBar.setSpacing(5);
        userBar.setAlignment(Pos.CENTER);

        return userBar;
    }
}
