package view.user.components;

import common.interfaces.IUser;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import manager.SessionManager;

public class TopBar extends HBox {
    private final IUser user = SessionManager.getInstance().getLoggedUser();

    public TopBar(TextField searchBar, Runnable onLogout, Runnable onSearchUpdate) {
        getStyleClass().add("top-bar");

        ContextMenu userMenu = new ContextMenu(
                CreateUserInfoItem(),
                new SeparatorMenuItem(),
                CreateProfileMenuItem(),
                CreateFavoritesMenuItem(),
                new SeparatorMenuItem(),
                CreateLogoutMenuItem(onLogout)
        );

        getChildren().addAll(CreateIcon(), CreateIcon(), CreateSpacer(), createSearchBox(searchBar, onSearchUpdate), CreateUserIcon(userMenu));
    }

    private Label CreateIcon(){
        Label icon = new Label("\uD83D\uDCDA");
        icon.getStyleClass().add("top-bar-icon");

        return icon;
    }

    private Label CreateTitle(){
        Label title = new Label("Digital-Library");
        title.getStyleClass().add("top-bar-title");

        return title;
    }

    private final Region CreateSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return spacer;
    }

    private HBox createSearchBox(TextField searchBar, Runnable onSearchUpdate) {
        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");

        Label searchIcon = new Label("\uD83D\uDD0D");
        searchIcon.getStyleClass().add("search-icon");

        searchBar.setPromptText("Search Book");
        searchBar.getStyleClass().add("search-bar");

        if (onSearchUpdate != null) {
            searchBar.textProperty().addListener((obs, oldText, newText) -> onSearchUpdate.run());
        }

        searchBox.getChildren().addAll(searchIcon, searchBar);

        return searchBox;
    }

    private Label CreateUserIcon(ContextMenu userMenu) {
        Label userIcon = new Label("\uD83D\uDC64");
        userIcon.getStyleClass().add("user-icon");

        userIcon.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                userMenu.show(userIcon, e.getScreenX(), e.getScreenY());
            }
        });

        return userIcon;
    }

    private Label CreateNameLabel(){
        Label nameLabel = new Label(user.getName() + " " + user.getSurname());
        nameLabel.getStyleClass().add("user-name-label");

        return nameLabel;
    }

    private Label CreateEmailLabel(){
        Label emailLabel = new Label(user.getEmail());
        emailLabel.getStyleClass().add("user-email-label");

        return emailLabel;
    }

    private final MenuItem CreateLogoutMenuItem(Runnable onLogout) {
        MenuItem logout = new MenuItem("Logout");
        logout.getStyleClass().add("menu-item-logout");

        logout.setOnAction(e -> {
            SessionManager.getInstance().logout();
            onLogout.run();
        });
        return logout;
    }

    private final CustomMenuItem CreateUserInfoItem() {
        Label nameLabel = CreateNameLabel();
        Label emailLabel = CreateEmailLabel();

        VBox userInfoBox = new VBox(nameLabel, emailLabel);
        userInfoBox.getStyleClass().add("user-info-box");

        CustomMenuItem userInfoItem = new CustomMenuItem(userInfoBox, false);
        userInfoItem.getStyleClass().add("user-info-item");

        return userInfoItem;
    }

    private MenuItem CreateProfileMenuItem() {
        MenuItem profile = new MenuItem("Profile");
        profile.getStyleClass().add("menu-item-profile");

        return profile;
    }

    private MenuItem CreateFavoritesMenuItem() {
        MenuItem favorites = new MenuItem("Favorites");
        favorites.getStyleClass().add("menu-item-favorites");

        return favorites;
    }
}

