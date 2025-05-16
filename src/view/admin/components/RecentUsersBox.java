package view.admin.components;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.User;

import java.util.List;

public class RecentUsersBox {

    private static final UserController userController = new UserController();

    public static VBox create(List<User> users) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setPrefWidth(450);
        box.getStyleClass().add("recentuserbox-style-1");

        Label title = new Label("Recent Users");
        title.getStyleClass().add("recentuserbox-style-2");

        box.getChildren().add(title);

        users.stream()
                .sorted((u1, u2) -> u2.getName().compareTo(u1.getName()))
                .limit(4)
                .forEach(user -> {
                    HBox row = new HBox(10);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(8, 0, 8, 0));

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

                    row.getChildren().addAll(initials, textBox);
                    box.getChildren().add(row);
                });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label viewAll = new Label("View All");
        viewAll.getStyleClass().add("recentuserbox-style-6");
        VBox.setMargin(viewAll, new Insets(10, 0, 0, 2));

        box.getChildren().addAll(spacer, viewAll);

        return box;
    }
}