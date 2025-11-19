package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showLogin();
    }

    @FXML
    private void onRegisterClicked() {
        // TODO: validate + call AuthService.register
        // If success:
        ScreenNavigator.showLogin();
        // else set errorLabel
    }
}
