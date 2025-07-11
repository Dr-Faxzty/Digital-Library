package com.example.view.admin;

import com.example.common.interfaces.IUser;
import com.example.common.observer.ViewObserver;
import com.example.common.observer.ViewSubject;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.manager.SessionManager;
import com.example.view.admin.components.Sidebar;

import java.util.HashMap;
import java.util.Map;

public class AdminView implements ViewObserver {
    private final IUser user;
    private final BorderPane layout = new BorderPane();
    private final Map<String, Pane> viewCache = new HashMap<>();

    public AdminView() {
        this.user = SessionManager.getInstance().getLoggedUser();
    }

    public void start(Stage stage) {
        setupLayout();
        setupScene(stage);
        stage.show();
    }

    private void setupLayout() {
        layout.setLeft(createSidebar());
        layout.setCenter(createInScrollPane(getOrCreateView("dashboard")));
    }

    private void setupScene(Stage stage) {
        Scene scene = new Scene(layout, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/style/admin.css").toExternalForm());
        stage.setTitle("Digital Library");
        stage.setScene(scene);
        stage.setResizable(true);
    }

    private Sidebar createSidebar() {
        Sidebar sidebar = new Sidebar(user);
        sidebar.setObserver(this);
        return sidebar;
    }

    private ScrollPane createInScrollPane(Pane view) {
        ScrollPane scrollPane = new ScrollPane(view);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }

    private Pane getOrCreateView(String viewName) {
        return viewCache.computeIfAbsent(viewName, this::createView);
    }

    private Pane createView(String viewName) {
        Pane view;
        switch (viewName) {
            case "dashboard" -> view = new Dashboard();
            case "books" -> view = new BooksSection();
            case "users" -> view = new UsersSection();
            case "loans" -> view = new LoansSection();
            default -> view = new Pane();
        }

        if (view instanceof ViewSubject subject) {
            subject.setObserver(this);
        }

        return view;
    }

    @Override
    public void onViewChange(String viewName) {
        Pane view = getOrCreateView(viewName);
        layout.setCenter(createInScrollPane(view));
    }
}
