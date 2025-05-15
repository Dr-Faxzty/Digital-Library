package view;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import manager.SessionManager;
import model.User;
import view.components.Sidebar;
import common.interfaces.observer.ViewObserver;

public class AdminView implements ViewObserver {
    private final User user;
    private final BorderPane layout = new BorderPane();

    public AdminView() {
        this.user = SessionManager.getInstance().getLoggedUser();
    }

    public void start(Stage stage) {
        Sidebar sidebar = new Sidebar(user);
        sidebar.setObserver(this);

        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setObserver(this);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        layout.setLeft(sidebar);
        layout.setCenter(scrollPane);

        Scene scene = new Scene(layout, 1000, 600);
        stage.setTitle("Digital Library");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void onViewChange(String viewName) {
        Pane view;
        switch (viewName) {
            case "dashboard" -> view = new AdminDashboard();
            case "books" -> view = new Pane();
            case "users" -> view = new Pane();
            case "settings" -> view = new Pane();
            default -> view = new Pane();
        }

        if (view instanceof common.interfaces.observer.ViewSubject subject) {
            subject.setObserver(this);
        }

        ScrollPane scrollPane = new ScrollPane(view);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        layout.setCenter(scrollPane);
    }
}