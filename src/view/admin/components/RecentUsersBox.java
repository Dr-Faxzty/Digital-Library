package view.admin.components;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import common.observer.ViewObserver;
import common.interfaces.IUser;

import java.util.List;
import java.util.stream.Collectors;

public class RecentUsersBox {

    public static VBox create(UserController userController, ViewObserver observer) {
        VBox root = createBaseLayout();
        Label title = createTitleLabel();
        ProgressIndicator loader = createLoader();

        root.getChildren().addAll(title, loader);

        userController.loadUsersAsync(
                users -> populateRecentUsers(root, users, loader, observer),
                () -> showError(root, loader)
        );

        return root;
    }

    private static VBox createBaseLayout() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setPrefWidth(450);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("recentuserbox-style-1");
        return box;
    }

    private static Label createTitleLabel() {
        Label title = new Label("Recent Users");
        title.getStyleClass().add("recentuserbox-style-2");
        return title;
    }

    private static ProgressIndicator createLoader() {
        ProgressIndicator loader = new ProgressIndicator();
        loader.setMaxSize(30, 30);
        VBox.setMargin(loader, new Insets(10, 0, 10, 0));
        return loader;
    }

    private static void populateRecentUsers(VBox root, List<IUser> users, ProgressIndicator loader, ViewObserver observer) {
        root.getChildren().remove(loader);
        root.setAlignment(Pos.TOP_LEFT);

        List<IUser> recentUsers = users.stream()
                .sorted((u1, u2) -> u2.getName().compareTo(u1.getName()))
                .limit(4)
                .collect(Collectors.toList());

        for (IUser user : recentUsers) {
            HBox row = createUserRow(user);
            root.getChildren().add(row);
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        root.getChildren().add(spacer);

        Label viewAll = createViewAllLabel(observer);
        root.getChildren().add(viewAll);
    }

    private static HBox createUserRow(IUser user) {
        String initialsText = ("" + user.getName().charAt(0) + user.getSurname().charAt(0)).toUpperCase();
        Label initials = new Label(initialsText);
        initials.getStyleClass().add("recentuserbox-style-3");
        initials.setMinWidth(30);
        initials.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(user.getName() + " " + user.getSurname());
        nameLabel.getStyleClass().add("recentuserbox-style-4");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.getStyleClass().add("recentuserbox-style-5");

        VBox textBox = new VBox(2, nameLabel, emailLabel);

        HBox row = new HBox(10, initials, textBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 0, 8, 0));
        return row;
    }

    private static Label createViewAllLabel(ViewObserver observer) {
        Label viewAll = new Label("View All");
        viewAll.getStyleClass().add("recentuserbox-style-6");
        VBox.setMargin(viewAll, new Insets(10, 0, 0, 2));
        viewAll.setOnMouseClicked(e -> {
            if (observer != null) observer.onViewChange("users");
        });
        return viewAll;
    }

    private static void showError(VBox root, ProgressIndicator loader) {
        root.getChildren().remove(loader);
        Label errorLabel = new Label("❌ Failed to load recent users.");
        root.getChildren().add(errorLabel);
    }
}