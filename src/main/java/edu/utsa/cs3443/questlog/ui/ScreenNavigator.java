package edu.utsa.cs3443.questlog.ui;

import java.io.IOException;

import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ScreenNavigator {

    private static final double WINDOW_WIDTH = 1024;
    private static final double WINDOW_HEIGHT = 768;

    private static Stage primaryStage;

    private ScreenNavigator() { }

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("QuestLog");
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setResizable(false);
    }

    private static void show(String fxmlPath) {
    try {
        FXMLLoader loader = new FXMLLoader(ScreenNavigator.class.getResource(fxmlPath));
        Parent root = loader.load();

        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        } else {
            primaryStage.getScene().setRoot(root);
        }

        primaryStage.show();

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showLogin() { show("/view/login/login.fxml"); }

    public static void showRegister() {
        show("/view/login/register.fxml");
    }

    public static void showDashboard() {
        show("/view/dashboard/dashboard.fxml");
    }
    
    public static void showManageAccount() {
    show("/view/settings/manage_account.fxml");
    }

public static void showAppearance() {
    show("/view/settings/appearance.fxml");
    }

public static void showCredits() {
    show("/view/settings/credits.fxml");
    }

    // NEW: create/edit entry
    public static void showEntryEditor(GameEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    ScreenNavigator.class.getResource("/view/entry/entry_editor.fxml")
            );
            Parent root = loader.load();

            EntryEditorController controller = loader.getController();
            controller.setEditingEntry(entry); // null = create, non-null = edit

            if (primaryStage.getScene() == null) {
                primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            } else {
                primaryStage.getScene().setRoot(root);
            }
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public static void showSettingsMenu() {
        show("/view/settings/settings_menu.fxml");
    }

    public static void showStats() {
        show("/view/stats/stats.fxml");
    }
}
