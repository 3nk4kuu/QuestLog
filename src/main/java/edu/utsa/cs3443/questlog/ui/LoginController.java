package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Label loginTitle; // added

    @FXML
    private void initialize() {
        // Hide error label
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }

        // Set vibrant green title programmatically
        if (loginTitle != null) {
            loginTitle.setStyle(
                    "-fx-text-fill: #00FF00; " +
                            "-fx-font-size: 80px; " +
                            "-fx-font-weight: bold;"
            );
        }
    }

    @FXML
    private void onLoginClicked() {
        // TODO: real auth later; for now go to dashboard
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onRegisterClicked() {
        ScreenNavigator.showRegister();
    }
}


