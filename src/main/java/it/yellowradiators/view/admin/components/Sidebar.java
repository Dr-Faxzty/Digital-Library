package it.yellowradiators.view.admin.components;

import it.yellowradiators.common.interfaces.IUser;
import it.yellowradiators.common.observer.ViewObserver;
import it.yellowradiators.common.observer.ViewSubject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.yellowradiators.manager.SessionManager;
import it.yellowradiators.view.LoginView;

import java.util.LinkedHashMap;
import java.util.Map;

public class Sidebar extends VBox implements ViewSubject {
    private ViewObserver observer;

    public Sidebar(IUser user) {
        setupLayout();
        getChildren().addAll(
                createTitle(),
                createNavButtons(),
                createSpacer(),
                createUserBar(user)
        );
    }

    @Override
    public void setObserver(ViewObserver observer) {
        this.observer = observer;
    }

    private void setupLayout() {
        getStyleClass().add("sidebar-style-1");
        setPrefWidth(220);
        setMinWidth(220);
        setMaxWidth(220);
    }

    private Label createTitle() {
        Label title = new Label("📚 Digital Library");
        title.getStyleClass().add("sidebar-style-2");
        return title;
    }

    private VBox createNavButtons() {
        VBox buttonsBox = new VBox(20);

        Map<String, String> navItems = new LinkedHashMap<>();
        navItems.put("📊 Dashboard", "dashboard");
        navItems.put("📚 Books", "books");
        navItems.put("👥 Users", "users");
        navItems.put("🔄 Loans", "loans");

        for (Map.Entry<String, String> entry : navItems.entrySet()) {
            Button btn = new Button(entry.getKey());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("sidebar-style-3");
            btn.setOnAction(e -> notifyObserver(entry.getValue()));
            buttonsBox.getChildren().add(btn);
        }

        return buttonsBox;
    }

    private Region createSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private HBox createUserBar(IUser user) {
        Label nameLabel = new Label(user.getName() + " " + user.getSurname());
        nameLabel.getStyleClass().add("sidebar-style-5");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.getStyleClass().add("sidebar-style-4");

        VBox userInfo = new VBox(4, nameLabel, emailLabel);

        Button logoutButton = new Button("⎋");
        logoutButton.getStyleClass().add("sidebar-style-5");
        logoutButton.setOnAction(e -> handleLogout());

        HBox userBar = new HBox(5, userInfo, logoutButton);
        userBar.setAlignment(Pos.CENTER);
        userBar.setPadding(new Insets(5));

        return userBar;
    }

    private void handleLogout() {
        SessionManager.getInstance().logout();
        new LoginView().start((Stage) getScene().getWindow());
    }

    private void notifyObserver(String viewName) {
        if (observer != null) {
            observer.onViewChange(viewName);
        }
    }
}