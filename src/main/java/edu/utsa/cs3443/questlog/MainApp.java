package edu.utsa.cs3443.questlog;

import edu.utsa.cs3443.questlog.service.FontLoader;
import edu.utsa.cs3443.questlog.ui.ScreenNavigator;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        FontLoader.loadAllFonts();

        ScreenNavigator.init(primaryStage);
        ScreenNavigator.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
