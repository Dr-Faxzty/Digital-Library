package com.example;

import javafx.application.Application;
import javafx.stage.Stage;
import com.example.view.LoginView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginView LoginView = new LoginView();
        LoginView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}