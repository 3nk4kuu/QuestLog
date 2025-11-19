package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class AppearanceController {

    @FXML private CheckBox darkModeCheckBox;

    @FXML
    private void initialize() {
        // TODO: read saved preference and set checkbox
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }

    @FXML
    private void onDarkModeToggled() {
        boolean dark = darkModeCheckBox.isSelected();
        // TODO: swap stylesheets on primaryStage
    }
}
