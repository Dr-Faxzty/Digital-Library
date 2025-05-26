package view;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import common.interfaces.IUser;
import manager.SessionManager;


public class UserView {
    private final IUser user;

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

        Scene scene = new Scene(scrollPane, 1000, 600);
        stage.setTitle("Digital Library");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

}
