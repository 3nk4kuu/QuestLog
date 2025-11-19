package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class ScreenNavigator {

    private static Stage primaryStage;

    private ScreenNavigator() { }

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("QuestLog");
    }

    public static void show(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: maybe show an Alert dialog
        }
    }

    public static void showLogin() {
        show("/view/login/login.fxml");
    }

    public static void showRegister() {
        show("/view/login/register.fxml");
    }

    public static void showDashboard() {
        show("/view/dashboard/dashboard.fxml");
    }

    public static void showEntryEditor() {
        show("/view/entry/entry_editor.fxml");
    }

    public static void showSettingsMenu() {
        show("/view/settings/settings_menu.fxml");
    }
    public static void showStats()  { 
        show("/view/stats/stats.fxml"); 
    }
    
}
