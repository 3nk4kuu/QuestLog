package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;

public class CreditsController {

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }
}
