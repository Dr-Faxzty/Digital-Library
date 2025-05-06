import javafx.application.Application;
import javafx.stage.Stage;
import view.RegistrationView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        RegistrationView registrationView = new RegistrationView();
        registrationView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}