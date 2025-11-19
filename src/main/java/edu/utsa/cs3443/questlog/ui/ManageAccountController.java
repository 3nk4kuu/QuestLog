package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ManageAccountController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        messageLabel.setText("");
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }

    @FXML
    private void onSaveEmailClicked() {
        // TODO: call AuthService.updateEmail()
        messageLabel.setText("Email updated.");
    }

    @FXML
    private void onSavePasswordClicked() {
        // TODO: validate + update password
        messageLabel.setText("Password updated.");
    }

    @FXML
    private void onDeleteAccountClicked() {
        // TODO: confirm + delete user via service
        ScreenNavigator.showLogin();
    }
}
