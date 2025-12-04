package edu.utsa.cs3443.questlog;

import edu.utsa.cs3443.questlog.ui.ScreenNavigator;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try (InputStream is = getClass().getResourceAsStream("/data/fonts/Jersey10-Regular.ttf")) {
            Font f = Font.loadFont(is, 16);
            System.out.println("Loaded font: " + (f == null ? "NULL" : f.getName() + " / " + f.getFamily()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScreenNavigator.init(primaryStage);
        ScreenNavigator.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
