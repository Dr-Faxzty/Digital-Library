package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import manager.SessionManager;
import model.User;
import view.admin.AdminView;
import view.UserView;


public class LoginController {
    private final UserController userController;

    public LoginController() {
        this.userController = new UserController();
    }

    public void handleLogin(String email, String password, Stage stage) {
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email and password are required.");
            return;
        }

        User user = userController.login(email, password);
        if (user != null) {
            SessionManager.getInstance().login(user);

            if (user.getRole().name().equals("ADMIN")) {
                AdminView adminView = new AdminView();
                adminView.start(stage);
            } else {
                UserView userView = new UserView();
                userView.start(stage);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed", "Incorrect email or password.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
