package com.example.view.user.components;

import com.example.common.interfaces.IUser;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import com.example.manager.SessionManager;

public class TopBar extends HBox {
    private final IUser user = SessionManager.getInstance().getLoggedUser();

    public TopBar(TextField searchBar, Runnable onLogout, Runnable onSearchUpdate) {
        getStyleClass().add("top-bar");

        ContextMenu userMenu = new ContextMenu(
                CreateUserInfoItem(),
                new SeparatorMenuItem(),
                CreateMenuItem("Profile", "menu-item-profile"),
                CreateMenuItem("Favorites", "menu-item-favorites"),
                new SeparatorMenuItem(),
                CreateLogoutMenuItem(onLogout)
        );

        getChildren().addAll(CreateLabel("\uD83D\uDCDA", "top-bar-icon"), CreateLabel("Digital-Library", "top-bar-title"), CreateSpacer(), createSearchBox(searchBar, onSearchUpdate), CreateUserIcon(userMenu));
    }

    private Label CreateLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);

        return label;
    }

    private Region CreateSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return spacer;
    }

    private HBox createSearchBox(TextField searchBar, Runnable onSearchUpdate) {
        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");

        Label searchIcon = CreateLabel("\uD83D\uDD0D", "search-icon");

        searchBar.setPromptText("Search Book");
        searchBar.getStyleClass().add("search-bar");

        if (onSearchUpdate != null) {
            searchBar.textProperty().addListener((obs, oldText, newText) -> onSearchUpdate.run());
        }

        searchBox.getChildren().addAll(searchIcon, searchBar);

        return searchBox;
    }

    private Label CreateUserIcon(ContextMenu userMenu) {
        Label userIcon = CreateLabel("\uD83D\uDC64", "user-icon");

        userIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                userMenu.show(userIcon, e.getScreenX(), e.getScreenY());
            }
        });

        return userIcon;
    }

    private MenuItem CreateLogoutMenuItem(Runnable onLogout) {
        MenuItem logout = new MenuItem("Logout");
        logout.getStyleClass().add("menu-item-logout");

        logout.setOnAction(e -> {
            SessionManager.getInstance().logout();
            onLogout.run();
        });
        return logout;
    }

    private CustomMenuItem CreateUserInfoItem() {
        Label nameLabel = CreateLabel(user.getName() + " " + user.getSurname(), "user-name-label");
        Label emailLabel = CreateLabel(user.getEmail(), "user-email-label");

        VBox userInfoBox = new VBox(nameLabel, emailLabel);
        userInfoBox.getStyleClass().add("user-info-box");

        CustomMenuItem userInfoItem = new CustomMenuItem(userInfoBox, false);
        userInfoItem.getStyleClass().add("user-info-item");

        return userInfoItem;
    }

    private MenuItem CreateMenuItem(String label, String styleClass) {
        MenuItem item = new MenuItem(label);
        item.getStyleClass().add(styleClass);

        return item;
    }
}

