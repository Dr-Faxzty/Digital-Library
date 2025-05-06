package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Role;
import manager.UserManager;
import model.User;
import persistence.JsonUserManager;
import view.LoginView;


public class RegistrationController {
    private final UserManager userManager;

    public RegistrationController() {
        this.userManager = UserManager.getInstance();
    }

    public void handleRegistration(String name, String surname, String username, String email, String password, Role role, Stage stage) {
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return;
        }

        User user = userManager.register(name, surname, username, email, password, role);
        if (user != null) {
            boolean saved = JsonUserManager.saveUsers(userManager.getAllUsers());
            if (saved) {
                showAlert(Alert.AlertType.INFORMATION, "Registration succeeded", "Now you can login.");
                new LoginView().start(stage);
            }
            else {
                showAlert(Alert.AlertType.ERROR, "Registration failed", "Could not save user data.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration failed", "Email already exists.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}