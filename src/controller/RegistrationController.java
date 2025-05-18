package controller;

import common.nullObject.NullUser;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import common.enums.Role;
import model.User;
import utils.InputValidator;
import view.LoginView;


public class RegistrationController {
    private final UserController userController;

    public RegistrationController() {
        this.userController = new UserController();
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

        User user = userController.register(name, surname, username, email, password, role);
        if (!(user instanceof NullUser)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration succeeded", "Now you can login.");
            new LoginView().start(stage);
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration failed", "Email already exists or saving failed.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}