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
        VBox container = new VBox(12);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.TOP_CENTER);
        container.getStyleClass().add("login-container");

        Label title = createLabel("Digital Library", "title");
        Label subtitle = createLabel("Login to your account", "subtitle");

        TextField emailField = createTextField("Your email");
        VBox emailBox = createInputBox("Email", emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        passwordField.getStyleClass().add("input");
        VBox passwordBox = createInputBox("Password", passwordField);

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
                emailBox,
                passwordBox,
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

        goToRegisterBtn.setOnAction(e -> new RegistrationView().start(stage));

        LoginController controller = new LoginController();
        loginBtn.setOnAction(e -> controller.handleLogin(emailField.getText(), passwordField.getText(), stage));
    }

    private VBox createInputBox(String labelText, Control inputField) {
        Label label = new Label(labelText);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.BASELINE_LEFT);
        label.getStyleClass().add("label");
        return new VBox(4, label, inputField);
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.getStyleClass().add("input");
        return field;
    }

    private Label createLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }
}