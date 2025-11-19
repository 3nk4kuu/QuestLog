package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;

public class SettingsMenuController {

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onAccountClicked() {
        ScreenNavigator.show("/view/settings/manage_account.fxml");
    }

    @FXML
    private void onAppearanceClicked() {
        ScreenNavigator.show("/view/settings/appearance.fxml");
    }

    @FXML
    private void onCreditsClicked() {
        ScreenNavigator.show("/view/settings/credits.fxml");
    }
}
