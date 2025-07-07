package com.example.controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.example.manager.SessionManager;
import com.example.utils.FxTaskRunner;
import com.example.view.admin.AdminView;
import com.example.view.user.UserView;


public class LoginController {
    private final UserController userController;

    public LoginController() {
        this.userController = UserController.getInstance();
    }

    public void handleLogin(String email, String password, Stage stage) {
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email and password are required.");
            return;
        }

        FxTaskRunner.runAsync(
            () -> userController.login(email, password),
            user -> {
                if (!(user.isNull())) {
                    SessionManager.getInstance().login(user);
                    if (user.getRole().name().equals("ADMIN")) {
                        new AdminView().start(stage);
                    } else {
                        new UserView().start(stage);
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login failed", "Incorrect email or password.");
                }
            },
            () -> showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error during login.")
        );

    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
