package it.yellowradiators.view.user;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class UserView {

    public UserView() {}

    private ScrollPane CreateScrollPane(){
        HomePage dashboard = new HomePage();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;");

        return scrollPane;
    }

    public void start(Stage stage) {
        Scene scene = new Scene(CreateScrollPane(), 1000, 600);
        stage.setTitle("Digital Library");
        stage.setResizable(true);
        stage.setScene(scene);

        stage.show();
    }

}
