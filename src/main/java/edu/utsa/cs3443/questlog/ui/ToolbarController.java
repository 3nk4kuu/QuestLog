// ToolbarController.java
package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class ToolbarController {

    @FXML private ToggleButton libraryButton;
    @FXML private ToggleButton statsButton;
    @FXML private ToggleButton settingsButton;

    @FXML
    private void initialize() {
        // default selection can be set per screen if you like
    }

    @FXML
    private void onLibraryClicked() {
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onStatsClicked() {
        ScreenNavigator.showStats();
    }

    @FXML
    private void onSettingsClicked() {
        ScreenNavigator.showSettingsMenu();
    }
}
