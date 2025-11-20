package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class AppearanceController {

    @FXML
    private ToggleButton darkModeToggle;

    @FXML
    private void initialize() {
        boolean enabled = ScreenNavigator.isDarkMode();
        darkModeToggle.setSelected(enabled);
        updateToggleText();
    }

    @FXML
    private void onDarkModeToggled() {
        boolean enabled = darkModeToggle.isSelected();
        ScreenNavigator.setDarkMode(enabled);
        updateToggleText();
    }

    private void updateToggleText() {
        darkModeToggle.setText(
                darkModeToggle.isSelected() ? "Dark Mode: On" : "Dark Mode: Off"
        );
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }
}
