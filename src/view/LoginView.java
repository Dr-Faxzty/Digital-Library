package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {
    public void start(Stage stage) {
        VBox container = new VBox(8);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.TOP_CENTER);
        container.getStyleClass().add("login-container");

        Label title = new Label("Digital Library");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Login to your account");
        subtitle.getStyleClass().add("subtitle");

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPromptText("Your email");
        emailLabel.setMaxWidth(Double.MAX_VALUE);
        emailLabel.setAlignment(Pos.BASELINE_LEFT);
        emailField.getStyleClass().add("input");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        passwordLabel.setMaxWidth(Double.MAX_VALUE);
        passwordLabel.setAlignment(Pos.BASELINE_LEFT);
        passwordField.getStyleClass().add("input");

        Label forgotPassword = new Label("Forgot your password?");
        forgotPassword.setMaxWidth(Double.MAX_VALUE);
        forgotPassword.setAlignment(Pos.BASELINE_RIGHT);
        forgotPassword.getStyleClass().add("link-label");


        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.getStyleClass().add("login-button");

        Label registerPrompt = new Label("Don't have an account? ");
        Button goToRegisterBtn = new Button("Register");
        goToRegisterBtn.getStyleClass().add("link-button");

        HBox registerBox = new HBox(registerPrompt, goToRegisterBtn);
        registerBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(
                title, subtitle,
                emailLabel, emailField,
                passwordLabel, passwordField,
                forgotPassword,
                loginBtn,
                registerBox
        );

        Scene scene = new Scene(container, 380, 400);
        scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());

        stage.setTitle("Digital Library - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        goToRegisterBtn.setOnAction(e -> {
            RegistrationView registrationView = new RegistrationView();
            registrationView.start(stage);
        });

        LoginController controller = new LoginController();
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            controller.handleLogin(email, password, stage);
        });
    }
}