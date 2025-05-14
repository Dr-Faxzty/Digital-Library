package view;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;
import manager.SessionManager;


public class UserView {
    private final User user;

    public UserView() {
        SessionManager session = SessionManager.getInstance();
        this.user = session.getLoggedUser();
    }

    public void start(Stage stage) {
        HomePage dashboard = new HomePage();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox vBox = new VBox(dashboard, scrollPane);

        Scene scene = new Scene(vBox, 1000, 600);
        stage.setTitle("Digital Library");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

}
