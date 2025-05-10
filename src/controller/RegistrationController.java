package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Role;
import manager.UserManager;
import model.User;
import persistence.JsonUserManager;
import utils.InputValidator;
import view.LoginView;


public class RegistrationController {
    private final UserManager userManager;
    private final JsonUserManager jsonUserManager;

    public RegistrationController() {
        this.userManager = UserManager.getInstance();
        this.jsonUserManager = new JsonUserManager();
    }

    public void handleRegistration(String name, String surname, String username, String email, String password, Role role, Stage stage) {
        if(!InputValidator.areFieldsFilled(name, surname, username, email, password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return;
        }
        if(!InputValidator.isEmailValid(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email format.");
            return;
        }

        if(!InputValidator.isTaxCodeValid(username)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid TaxCode format.");
            return;
        }

        if(!InputValidator.isPasswordValid(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 6 characters long.");
            return;
        }

        User user = userManager.register(name, surname, username, email, password, role);
        if (user != null) {
            boolean saved = jsonUserManager.save(userManager.getAll());
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