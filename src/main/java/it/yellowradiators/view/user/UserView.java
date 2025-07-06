package it.yellowradiators.view.user;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class UserView {

    public UserView() {}

    private ScrollPane createScrollPane() {
        it.yellowradiators.view.user.HomePage homePage = new it.yellowradiators.view.user.HomePage();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(homePage);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;");

        return scrollPane;
    }

    public void start(Stage stage) {
        Platform.runLater(() -> {
            ScrollPane scrollPane = createScrollPane();
            Scene scene = new Scene(scrollPane, 1000, 600);
            stage.setTitle("Digital Library");
            stage.setResizable(true);
            stage.setScene(scene);
            stage.show();
        });
    }
}
