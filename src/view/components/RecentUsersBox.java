package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import manager.UserManager;
import model.User;

import java.util.List;

public class RecentUsersBox {
    public static VBox create() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setPrefWidth(450);
        box.setStyle("-fx-background-color: white; -fx-padding: 16; -fx-background-radius: 10;");

        Label title = new Label("Recent Users");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        box.getChildren().add(title);

        List<User> users = UserManager.getInstance().getAll();

        users.stream()
                .sorted((u1, u2) -> u2.getName().compareTo(u1.getName()))
                .limit(4)
                .forEach(user -> {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(8, 0, 8, 0));

            String initialsText = ("" + user.getName().charAt(0) + user.getSurname().charAt(0)).toUpperCase();
            Label initials = new Label(initialsText);
            initials.setStyle("-fx-background-color: #eee; -fx-text-fill: #222; -fx-font-weight: bold; " +
                    "-fx-alignment: center; -fx-font-size: 16px; -fx-padding: 6 10; -fx-background-radius: 20;");
            initials.setMinWidth(30);
            initials.setAlignment(Pos.CENTER);

            Label nameLabel = new Label(user.getName() + " " + user.getSurname());
            nameLabel.setStyle("-fx-font-weight: bold;");

            Label emailLabel = new Label(user.getEmail());
            emailLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #777;");

            VBox textBox = new VBox(2, nameLabel, emailLabel);


            row.getChildren().addAll(initials, textBox);
            box.getChildren().add(row);
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label viewAll = new Label("View All");
        viewAll.setStyle("-fx-text-fill: #007BFF; -fx-font-size: 12px; -fx-cursor: hand;");
        VBox.setMargin(viewAll, new Insets(10, 0, 0, 2));

        box.getChildren().addAll(spacer, viewAll);

        return box;
    }
}
