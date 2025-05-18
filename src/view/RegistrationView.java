package view;

import controller.RegistrationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import common.enums.Role;

public class RegistrationView {
    public void start(Stage stage) {
        VBox container = new VBox(20);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("login-container");

        Label title = new Label("Digital Library");
        title.getStyleClass().add("register-title");

        Label subtitle = new Label("Create your account");
        subtitle.getStyleClass().add("register-subtitle");


        Label nameLabel = new Label("Name");
        nameLabel.getStyleClass().add("label");
        TextField nameField = new TextField();
        nameField.setPromptText("Your name");
        nameField.getStyleClass().add("input");
        nameField.setPrefWidth(130);

        Label surnameLabel = new Label("Surname");
        surnameLabel.getStyleClass().add("label");
        TextField surnameField = new TextField();
        surnameField.setPromptText("Your surname");
        surnameField.getStyleClass().add("input");
        surnameField.setPrefWidth(130);

        VBox nameBox = new VBox(4, nameLabel, nameField);
        VBox surnameBox = new VBox(4, surnameLabel, surnameField);

        HBox nameRow = new HBox(16, nameBox, surnameBox);
        nameRow.setAlignment(Pos.CENTER);
        nameRow.getStyleClass().add("register-row");


        Label taxCodeLabel = new Label("Tax Code");
        taxCodeLabel.getStyleClass().add("label");
        TextField taxCodeField = new TextField();
        taxCodeField.setPromptText("Your tax code");
        taxCodeField.getStyleClass().add("input");
        VBox taxCodeBox = new VBox(4, taxCodeLabel, taxCodeField);


        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("label");
        TextField emailField = new TextField();
        emailField.setPromptText("Your email");
        emailField.getStyleClass().add("input");
        VBox emailBox = new VBox(4, emailLabel, emailField);


        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("label");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        passwordField.getStyleClass().add("input");
        VBox passwordBox = new VBox(4, passwordLabel, passwordField);


        Label roleLabel = new Label("Select your role: ");
        roleLabel.getStyleClass().add("role-label");

        RadioButton userRadio = new RadioButton("User");
        RadioButton adminRadio = new RadioButton("Admin");

        ToggleGroup roleGroup = new ToggleGroup();
        userRadio.setToggleGroup(roleGroup);
        adminRadio.setToggleGroup(roleGroup);
        userRadio.setSelected(true);

        HBox roleBox = new HBox(4, roleLabel, userRadio, adminRadio);
        roleBox.setAlignment(Pos.CENTER);


        Button registerBtn = new Button("Register");
        registerBtn.getStyleClass().add("register-button");
        VBox.setMargin(registerBtn, new Insets(10, 0, 0, 0));


        Label loginPrompt = new Label("Already have an account? ");
        Button goToLoginBtn = new Button("Login");
        goToLoginBtn.getStyleClass().add("link-button");

        HBox loginBox = new HBox(loginPrompt, goToLoginBtn);
        loginBox.setAlignment(Pos.CENTER);


        container.getChildren().addAll(
                title,
                subtitle,
                nameRow,
                taxCodeBox,
                emailBox,
                passwordBox,
                roleBox,
                registerBtn,
                loginBox
        );

        Scene scene = new Scene(container, 450, 600);
        scene.getStylesheets().add(getClass().getResource("/style/register.css").toExternalForm());

        stage.setTitle("Digital Library - Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        goToLoginBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.start(stage);
        });

        RegistrationController controller = new RegistrationController();

        registerBtn.setOnAction(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String taxCode = taxCodeField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Role role = userRadio.isSelected() ? Role.USER : Role.ADMIN;

            controller.handleRegistration(name, surname, taxCode, email, password, role, stage);
        });
    }
}
