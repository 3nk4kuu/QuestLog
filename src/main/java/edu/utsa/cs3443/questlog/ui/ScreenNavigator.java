package edu.utsa.cs3443.questlog.ui;

import java.io.IOException;

import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ScreenNavigator {

    private static Stage primaryStage;

    private ScreenNavigator() { }

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("QuestLog");
    }

    private static void show(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
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

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
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
