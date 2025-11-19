package edu.utsa.cs3443.questlog;

import edu.utsa.cs3443.questlog.ui.ScreenNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ScreenNavigator.init(primaryStage);
        ScreenNavigator.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
