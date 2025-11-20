package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void initialize() {
        // Guard against FXML issues; but if everything is wired, this will be non-null
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }

    @FXML
    private void onLoginClicked() {
        // TODO: real auth later; for now always go to dashboard
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onRegisterClicked() {
        ScreenNavigator.showRegister();
    }
}
