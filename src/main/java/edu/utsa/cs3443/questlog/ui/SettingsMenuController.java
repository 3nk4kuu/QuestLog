package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;

public class SettingsMenuController {

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onAccountClicked() {
        ScreenNavigator.showManageAccount();
    }

    @FXML
    private void onAppearanceClicked() {
        ScreenNavigator.showAppearance();
    }

    @FXML
    private void onCreditsClicked() {
        ScreenNavigator.showCredits();
    }

}
