package com.example.controller;

import com.example.common.enums.Role;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.example.utils.FxTaskRunner;
import com.example.utils.InputValidator;
import com.example.view.LoginView;


public class RegistrationController {
    private final UserController userController;

    public RegistrationController() {
        this.userController = UserController.getInstance();
    }

    public void handleRegistration(String name, String surname, String taxCode, String email, String password, Role role, Stage stage) {
        if(!InputValidator.areFieldsFilled(name, surname, taxCode, email, password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return;
        }
        if(!InputValidator.isEmailValid(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email format.");
            return;
        }

        if(!InputValidator.isTaxCodeValid(taxCode)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid TaxCode format.");
            return;
        }

        if(!InputValidator.isPasswordValid(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 6 characters long.");
            return;
        }

        FxTaskRunner.runAsync(
            () -> userController.register(name, surname, taxCode, email, password, role),
        user -> {
                if (!(user.isNull())) {
                    showAlert(Alert.AlertType.INFORMATION, "Registration succeeded", "Now you can login.");
                    new LoginView().start(stage);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Registration failed", "Email already exists or saving failed.");
                }
            },
            () -> showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error during registration.")
        );
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}