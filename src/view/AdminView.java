package view;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import manager.SessionManager;
import model.User;
import view.components.Sidebar;

public class AdminView {
    private final User user;

    public AdminView() {
        SessionManager session = SessionManager.getInstance();
        this.user = session.getLoggedUser();
    }

    public void start(Stage stage) {
        Sidebar sidebar = new Sidebar(user);
        AdminDashboard dashboard = new AdminDashboard();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        HBox root = new HBox(sidebar, scrollPane);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Digital Library - Admin Dashboard");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }
}