package it.yellowradiators.view;

import it.yellowradiators.common.enums.Role;
import it.yellowradiators.controller.RegistrationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationView {
    public void start(Stage stage) {
        VBox container = new VBox(20);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("login-container");

        Label title = createLabel("Digital Library", "register-title");
        Label subtitle = createLabel("Create your account", "register-subtitle");


        TextField nameField = createTextField("Your name");
        TextField surnameField = createTextField("Your surname");

        VBox nameBox = createInputBox("Name", nameField);
        VBox surnameBox = createInputBox("Surname", surnameField);

        HBox nameRow = new HBox(16, nameBox, surnameBox);
        nameRow.setAlignment(Pos.CENTER);
        nameRow.getStyleClass().add("register-row");


        TextField taxCodeField = createTextField("Your tax code");
        VBox taxCodeBox = createInputBox("Tax Code", taxCodeField);

        TextField emailField = createTextField("Your email");
        VBox emailBox = createInputBox("Email", emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        passwordField.getStyleClass().add("input");
        VBox passwordBox = createInputBox("Password", passwordField);


        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton userRadio = new RadioButton("User");
        RadioButton adminRadio = new RadioButton("Admin");
        userRadio.setToggleGroup(roleGroup);
        adminRadio.setToggleGroup(roleGroup);
        userRadio.setSelected(true);

        Label roleLabel = createLabel("Select your role: ", "role-label");
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
                title, subtitle, nameRow,
                taxCodeBox, emailBox, passwordBox,
                roleBox, registerBtn, loginBox
        );

        Scene scene = new Scene(container, 450, 600);
        scene.getStylesheets().add(getClass().getResource("/style/register.css").toExternalForm());

        stage.setTitle("Digital Library - Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        goToLoginBtn.setOnAction(e -> new LoginView().start(stage));

        RegistrationController controller = new RegistrationController();
        registerBtn.setOnAction(e -> {
            Role role = userRadio.isSelected() ? Role.USER : Role.ADMIN;
            controller.handleRegistration(
                    nameField.getText(),
                    surnameField.getText(),
                    taxCodeField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    role,
                    stage
            );
        });
    }

    private VBox createInputBox(String labelText, Control inputField) {
        Label label = createLabel(labelText, "label");
        return new VBox(4, label, inputField);
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.getStyleClass().add("input");
        field.setPrefWidth(130);
        return field;
    }

    private Label createLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }
}
